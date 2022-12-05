package com.example.semestralnezadanie.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.semestralnezadanie.R
import com.example.semestralnezadanie.api.FriendContact
import com.example.semestralnezadanie.databinding.FragmentFriendAddBinding
import com.example.semestralnezadanie.databinding.FragmentFriendRemoveBinding
import com.example.semestralnezadanie.fragments.viewmodels.FriendsViewModel
import com.example.semestralnezadanie.fragments.viewmodels.ViewModelHelper
import com.google.android.material.textfield.TextInputLayout


class FriendRemoveFragment : Fragment()
{

    private var _binding : FragmentFriendRemoveBinding? = null
    private val binding get() = _binding!!

    private lateinit var usernameField : TextInputLayout
    private lateinit var deleteFriendBtn : Button

    private val friendViewModel : FriendsViewModel by lazy {
        ViewModelProvider(this, ViewModelHelper.provideFriendViewModelFactory(requireContext()))[FriendsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        _binding = FragmentFriendRemoveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        usernameField = binding.usernameFieldRemove
        deleteFriendBtn = binding.deleteFriendBtn

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            friendmodel = friendViewModel
        }

        deleteFriendBtn.setOnClickListener {
            friendViewModel.removeFriend(FriendContact(usernameField.editText?.text.toString()))
        }
    }
}