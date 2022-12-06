package com.example.semestralnezadanie.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
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
    private lateinit var builder : AlertDialog.Builder

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

        builder = AlertDialog.Builder(requireContext())

        deleteFriendBtn.setOnClickListener {
            if(usernameField.editText?.text.toString().isBlank())
            {
                Toast.makeText(requireContext(), "Zadajte meno priateľa", Toast.LENGTH_SHORT).show()
            }
            else
            {
                builder.setTitle("Odstránenie priateľa")
                    .setMessage("Naozaj chcete odstrániť " + usernameField.editText?.text.toString() +"?")
                    .setCancelable(true)
                    .setPositiveButton("Áno"){dialogInterface, it ->
                        friendViewModel.removeFriend(FriendContact(usernameField.editText?.text.toString()))
                        Toast.makeText(requireContext(), "Odstránenie úspešné", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(requireView()).navigate(R.id.action_friendRemoveFragment_to_friendsFragment)
                    }
                    .setNegativeButton("Nie"){dialogInterface, it ->
                        dialogInterface.cancel()
                    }
                    .show()
            }

        }



    }
}