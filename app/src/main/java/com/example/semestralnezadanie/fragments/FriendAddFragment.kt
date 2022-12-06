package com.example.semestralnezadanie.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.semestralnezadanie.R
import com.example.semestralnezadanie.api.FriendContact
import com.example.semestralnezadanie.databinding.FragmentFriendAddBinding
import com.example.semestralnezadanie.fragments.viewmodels.FriendsViewModel
import com.example.semestralnezadanie.fragments.viewmodels.ViewModelHelper
import com.google.android.material.textfield.TextInputLayout


class FriendAddFragment : Fragment()
{
    private var _binding : FragmentFriendAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var usernameField : TextInputLayout
    private lateinit var addButton : Button
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
        _binding = FragmentFriendAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        usernameField = binding.usernameFieldAdd
        addButton = binding.addFriendBtn

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            friendmodel = friendViewModel
        }

        builder = AlertDialog.Builder(requireContext())

        addButton.setOnClickListener {

            if(usernameField.editText?.text.toString().isBlank())
            {
                Toast.makeText(requireContext(), "Zadajte meno priateľa", Toast.LENGTH_SHORT).show()
            }
            else
            {
                builder.setTitle("Pridanie priateľa")
                    .setMessage("Naozaj chcete pridať " + usernameField.editText?.text.toString() +"?")
                    .setCancelable(true)
                    .setPositiveButton("Áno"){dialogInterface, it ->
                        friendViewModel.addFriend(FriendContact(usernameField.editText?.text.toString()))
                        Toast.makeText(requireContext(), "Pridanie úspešné", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(requireView()).navigate(R.id.action_addFriendFragment_to_friendsFragment)
                    }
                    .setNegativeButton("Nie"){dialogInterface, it ->
                        dialogInterface.cancel()
                    }
                    .show()
            }
        }
    }
}