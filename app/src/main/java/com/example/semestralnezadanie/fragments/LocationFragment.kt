package com.example.semestralnezadanie.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.semestralnezadanie.GeofenceBroadcastReceiver
import com.example.semestralnezadanie.R
import com.example.semestralnezadanie.adapters.NearbyPubAdapter
import com.example.semestralnezadanie.database.preferences.Preferences
import com.example.semestralnezadanie.databinding.FragmentLocationBinding
import com.example.semestralnezadanie.entities.UserCurrentLocation
import com.example.semestralnezadanie.fragments.viewmodels.LocationViewModel
import com.example.semestralnezadanie.fragments.viewmodels.ViewModelHelper
import com.google.android.gms.location.*
import com.google.android.material.floatingactionbutton.FloatingActionButton


class LocationFragment : Fragment()
{
    private var _binding : FragmentLocationBinding? = null
    private val binding get() = _binding!!
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var geofencingClient: GeofencingClient
    private lateinit var checkInBtn : Button
    private lateinit var recyclerView : RecyclerView
    private lateinit var refreshBtn : FloatingActionButton

    private val locationViewModel : LocationViewModel by lazy {
        ViewModelProvider(this, ViewModelHelper.providePubViewModelFactory(requireContext()))[LocationViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_BACKGROUND_LOCATION, false) ->
            {
                Log.d("Location", "Granted")
            }
            else ->
            {
                locationViewModel.showMessage("Background location access denied.")
                Log.d("Location", "Not granted")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        geofencingClient = LocationServices.getGeofencingClient(requireActivity())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.locationRecyclerView
        checkInBtn = binding.checkInBtn
        refreshBtn = binding.btnRefresh

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = NearbyPubAdapter(requireContext())

        val preferences = Preferences.getInstance().getUserItem(requireContext())
        if((preferences?.userId ?: "").isBlank())
        {
            Navigation.findNavController(view).navigate(R.id.action_locationFragment_to_loginFragment)
            return
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            locationmodel = locationViewModel
        }

        refreshBtn.setOnClickListener {

        }

        checkInBtn.setOnClickListener {
            if(checkBackgroundPermissions())
            {
                locationViewModel.checkInUser()
            }
            else
            {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                {
                    showPermissionAlertDialog()
                }
            }
        }

        locationViewModel.nearbyPubs.observe(viewLifecycleOwner)
        {
            it?.apply {
                (recyclerView.adapter!! as NearbyPubAdapter).data = it
            }
        }

        locationViewModel.checkedIn.observe(viewLifecycleOwner)
        {
            it?.getContentIfNotHandled().let {
                if(it == true)
                {
                    locationViewModel.showMessage("Checked in")
                    locationViewModel.userLocation.value?.let {
                        createFence(it.latitude, it.longitude)
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showPermissionAlertDialog()
    {
        val alertDialog: AlertDialog = requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle("Background location needed")
                setMessage("Allow background location (All times) for detecting when you leave bar.")
                setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, id ->
                        locationPermissionRequest.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION
                            )
                        )
                    })
                setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            }
            // Create the AlertDialog
            builder.create()
        }
        alertDialog.show()
    }

    private fun checkPermissions() : Boolean
    {
        return ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun loadData() {
        if (checkPermissions()) {
            locationViewModel.loadData.postValue(true)
            fusedLocationProviderClient.getCurrentLocation(
                CurrentLocationRequest.Builder().setDurationMillis(30000)
                    .setMaxUpdateAgeMillis(60000).build(), null
            ).addOnSuccessListener {
                it?.let {
                    locationViewModel.userLocation.postValue(UserCurrentLocation(it.latitude, it.longitude))
                } ?: locationViewModel.loadData.postValue(false)
            }
        }
    }

    @SuppressLint("MissingPermission", "UnspecifiedImmutableFlag")
    private fun createFence(lat: Double, lon: Double) {
        if (!checkPermissions()) {
            locationViewModel.showMessage("Geofence failed, permissions not granted.")
        }
        val geofenceIntent = PendingIntent.getBroadcast(
            requireContext(), 0,
            Intent(requireContext(), GeofenceBroadcastReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val request = GeofencingRequest.Builder().apply {
            addGeofence(
                Geofence.Builder()
                    .setRequestId("mygeofence")
                    .setCircularRegion(lat, lon, 300F)
                    .setExpirationDuration(1000L * 60 * 60 * 24)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build()
            )
        }.build()

        geofencingClient.addGeofences(request, geofenceIntent).run {
            addOnSuccessListener {
                Navigation.findNavController(requireView()).navigate(R.id.action_locationFragment_to_recyclerFragment)
            }
            addOnFailureListener {
                locationViewModel.showMessage("Geofence failed to create.") //permission is not granted for All times.
                it.printStackTrace()
            }
        }
    }

    private fun checkBackgroundPermissions(): Boolean
    {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }
        else
        {
            return true
        }
    }
}