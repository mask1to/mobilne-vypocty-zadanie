package com.example.semestralnezadanie.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.semestralnezadanie.R
import com.example.semestralnezadanie.adapters.PubAdapter
import com.example.semestralnezadanie.database.LocalCache
import com.example.semestralnezadanie.database.preferences.Preferences
import com.example.semestralnezadanie.databinding.FragmentRegistrationBinding
import com.example.semestralnezadanie.databinding.RecyclerFragmentBinding
import com.example.semestralnezadanie.fragments.viewmodels.LoginRegisterViewModel
import com.example.semestralnezadanie.fragments.viewmodels.PubsViewModel
import com.example.semestralnezadanie.fragments.viewmodels.ViewModelHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException


class RecyclerFragment : Fragment()
{
    private var _binding : RecyclerFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var friendsButton : FloatingActionButton
    private lateinit var sortingFloating : FloatingActionButton
    private lateinit var logoutButton: FloatingActionButton
    private lateinit var closePubsBtn : FloatingActionButton

    companion object{
        var isSorted : Boolean = true
    }

    private val pubViewModel : PubsViewModel by lazy {
        ViewModelProvider(this, ViewModelHelper.providePubViewModelFactory(requireContext()))[PubsViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        parentFragment?.activity?.actionBar?.hide()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
    {
        _binding = RecyclerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        //recyclerView = view.findViewById(R.id.my_recycler_view)
        recyclerView = binding.myRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = PubAdapter(requireContext())

        sortingFloating = binding.btnSort
        logoutButton = binding.btnLogout
        friendsButton = binding.btnFriends
        closePubsBtn = binding.btnClosePubs

        val preferences = Preferences.getInstance().getUserItem(requireContext())
        if((preferences?.userId ?: "").isBlank())
        {
            Navigation.findNavController(view).navigate(R.id.action_recyclerFragment_to_loginFragment)
            return
        }

        checkPermissions()

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            pubmodel = pubViewModel
        }

        sortingFloating.setOnClickListener {
            //isSorted = !isSorted
            //recyclerView.adapter = PubAdapter(requireContext())
            Toast.makeText(context, "click sort", Toast.LENGTH_SHORT).show()
            Log.d("click", "click")
            pubViewModel.sortPubsByName()
        }

        closePubsBtn.setOnClickListener {
            val action = RecyclerFragmentDirections.actionRecyclerFragmentToLocationFragment()
            Navigation.findNavController(view).navigate(action)
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

        super.onViewCreated(view, savedInstanceState)
    }

    /*
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return when(item.itemId){
            R.id.switch_sort -> {
                isSorted = !isSorted
                showIcon(item)

                return true
            }

            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        inflater.inflate(R.menu.sorting_menu, menu)

        val btn = menu.findItem(R.id.switch_sort)
        showIcon(btn)
    }

    private fun showIcon(item : MenuItem?)
    {
        if(item == null)
        {
            return
        }

        item.icon =
            if(isSorted)
                ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_baseline_sort_24)
            else
                ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_baseline_sort_by_alpha_24)
    }*/

    /*private fun sortPubs()
    {
        pubViewModel.sortPubsByName()
        //recyclerView.adapter?.notifyDataSetChanged()
    }*/

    private fun checkPermissions() : Boolean
    {
        return ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
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