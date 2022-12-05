package com.example.semestralnezadanie.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.semestralnezadanie.R
import com.example.semestralnezadanie.adapters.PubAdapter
import com.example.semestralnezadanie.database.preferences.Preferences
import com.example.semestralnezadanie.databinding.RecyclerFragmentBinding
import com.example.semestralnezadanie.entities.User
import com.example.semestralnezadanie.entities.UserCurrentLocation
import com.example.semestralnezadanie.fragments.viewmodels.PubsViewModel
import com.example.semestralnezadanie.fragments.viewmodels.ViewModelHelper
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.floatingactionbutton.FloatingActionButton


class RecyclerFragment : Fragment()
{
    private var _binding : RecyclerFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var friendsButton : FloatingActionButton
    private lateinit var sortingFloating : FloatingActionButton
    private lateinit var logoutButton: FloatingActionButton
    private lateinit var closePubsBtn : FloatingActionButton
    private lateinit var refreshBtn : FloatingActionButton
    private lateinit var sortDistance : FloatingActionButton
    private lateinit var animationLottie : LottieAnimationView
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val pubViewModel : PubsViewModel by lazy {
        ViewModelProvider(this, ViewModelHelper.providePubViewModelFactory(requireContext()))[PubsViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                loadData()
                setupAnimation(animationLottie)
                // Precise location access granted.
                Log.d("hey", "hey1")
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                loadData()
                setupAnimation(animationLottie)
                pubViewModel.showMessage("Only approximate location access granted.")
                // Only approximate location access granted.
            }
            else -> {
                pubViewModel.showMessage("Location access denied.")
                // No location access granted.
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        parentFragment?.activity?.actionBar?.hide()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
    {
        _binding = RecyclerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.myRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = PubAdapter(requireContext())

        sortingFloating = binding.btnSort
        logoutButton = binding.btnLogout
        friendsButton = binding.btnFriends
        closePubsBtn = binding.btnClosePubs
        refreshBtn = binding.btnRefreshPubs
        sortDistance = binding.btnSortDistance
        animationLottie = binding.progressBar

        val preferences = Preferences.getInstance().getUserItem(requireContext())
        if((preferences?.userId ?: "").isBlank())
        {
            Navigation.findNavController(view).navigate(R.id.action_recyclerFragment_to_loginFragment)
            return
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            pubmodel = pubViewModel
        }
        if(checkPermissions())
        {
            setupAnimation(animationLottie)
            loadData()
        }
        else
        {
            locationPermissionRequest.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
        }

        // sorted by users_count by default
        pubViewModel.sortByUsersCount()

        refreshBtn.setOnClickListener {
            setupAnimation(animationLottie)
            loadData()
        }

        sortDistance.setOnClickListener{
            pubViewModel.sortByDistance(pubViewModel.userLocation.value!!.userLatitude, pubViewModel.userLocation.value!!.userLongitude)
        }

        sortingFloating.setOnClickListener {
            pubViewModel.sortByName()
        }

        closePubsBtn.setOnClickListener {
            if(checkPermissions())
            {
                val action = RecyclerFragmentDirections.actionRecyclerFragmentToLocationFragment()
                Navigation.findNavController(view).navigate(action)
            }
            else
            {
                locationPermissionRequest.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ))

            }
        }

        friendsButton.setOnClickListener {
            val action = RecyclerFragmentDirections.actionRecyclerFragmentToFriendsFragment()
            Navigation.findNavController(view).navigate(action)
        }

        logoutButton.setOnClickListener {
            Preferences.getInstance().clearAllData(requireContext())
            val action = RecyclerFragmentDirections.actionRecyclerFragmentToLoginFragment()
            Navigation.findNavController(view).navigate(action)
        }

        pubViewModel.allPubs.observe(viewLifecycleOwner)
        {
            it?.apply {
                (recyclerView.adapter!! as PubAdapter).data = it
            }
        }

        pubViewModel.message.observe(viewLifecycleOwner)
        {
           if(Preferences.getInstance().getUserItem(requireContext()) == null)
           {
               val action = RecyclerFragmentDirections.actionRecyclerFragmentToLoginFragment()
               Navigation.findNavController(view).navigate(action)
           }
        }


    }

    @SuppressLint("MissingPermission")
    private fun loadData() {
        if (checkPermissions()) {
            pubViewModel.loadData.postValue(true)
            fusedLocationProviderClient.getCurrentLocation(
                CurrentLocationRequest.Builder().setDurationMillis(30000)
                    .setMaxUpdateAgeMillis(60000).build(), null
            ).addOnSuccessListener {
                it?.let {
                    pubViewModel.userLocation.postValue(UserCurrentLocation(it.latitude, it.longitude))
                } ?: pubViewModel.loadData.postValue(false)
            }
        }
    }

    private fun checkPermissions(): Boolean
    {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("Range")
    private fun setupAnimation(animationView: LottieAnimationView)
    {
        animationView.speed = 1.5F // How fast does the animation play
        animationView.progress = 50F // Starts the animation from 50% of the beginning
        animationView.setAnimation(R.raw.beer)
        animationView.repeatCount = LottieDrawable.INFINITE
        animationView.playAnimation()

    }

}