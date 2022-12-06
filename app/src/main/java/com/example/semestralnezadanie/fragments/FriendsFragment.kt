package com.example.semestralnezadanie.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.semestralnezadanie.R
import com.example.semestralnezadanie.adapters.FriendAdapter
import com.example.semestralnezadanie.database.preferences.Preferences
import com.example.semestralnezadanie.databinding.FragmentFriendsBinding
import com.example.semestralnezadanie.databinding.FragmentRegistrationBinding
import com.example.semestralnezadanie.fragments.viewmodels.FriendsViewModel
import com.example.semestralnezadanie.fragments.viewmodels.LoginRegisterViewModel
import com.example.semestralnezadanie.fragments.viewmodels.ViewModelHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton


class FriendsFragment : Fragment()
{

    private var _binding : FragmentFriendsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView : RecyclerView
    private lateinit var addFriendBtn : FloatingActionButton
    private lateinit var removeFriendBtn : FloatingActionButton
    private lateinit var refreshFriendsBtn : FloatingActionButton
    private lateinit var lottieAnimationFriends : LottieAnimationView

    private val friendViewModel : FriendsViewModel by lazy {
        ViewModelProvider(this, ViewModelHelper.provideFriendViewModelFactory(requireContext()))[FriendsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setupAnimation(lottieAnimationFriends)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        recyclerView = binding.friendsRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = FriendAdapter(requireContext())

        addFriendBtn = binding.btnAddFriend
        removeFriendBtn = binding.btnRemoveFriend
        refreshFriendsBtn = binding.btnRefreshFriends
        lottieAnimationFriends = binding.lottieAnimationFriends

        val preferences = Preferences.getInstance().getUserItem(requireContext())
        if((preferences?.userId ?: "").isBlank())
        {
            Log.d("preferences", preferences.toString())
            /*val action = RegistrationFragmentDirections.actionRegistrationFragmentToRecyclerFragment()
            Navigation.findNavController(view).navigate(action)*/
            return
        }
        else
        {
            friendViewModel.getFriendList(preferences!!.userId.toLong())
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            friendmodel = friendViewModel
        }

        refreshFriendsBtn.setOnClickListener {
            refreshFriends(friendViewModel, preferences.userId.toLong())
        }

        addFriendBtn.setOnClickListener {
            val action = FriendsFragmentDirections.actionFriendsFragmentToAddFriendFragment()
            Navigation.findNavController(view).navigate(action)
        }

        removeFriendBtn.setOnClickListener {
            val action = FriendsFragmentDirections.actionFriendsFragmentToFriendRemoveFragment()
            Navigation.findNavController(view).navigate(action)
        }

        friendViewModel.allFriends?.observe(viewLifecycleOwner)
        {
            it?.apply {
                (recyclerView.adapter!! as FriendAdapter).data = it
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun refreshFriends(friendsViewModel: FriendsViewModel, id : Long)
    {
        friendsViewModel.refreshAll(id)
    }

    @SuppressLint("Range")
    private fun setupAnimation(animationView: LottieAnimationView)
    {
        animationView.speed = 1.5F // How fast does the animation play
        animationView.progress = 50F // Starts the animation from 50% of the beginning
        animationView.setAnimation(R.raw.friends)
        animationView.repeatCount = LottieDrawable.INFINITE
        animationView.playAnimation()

    }

}