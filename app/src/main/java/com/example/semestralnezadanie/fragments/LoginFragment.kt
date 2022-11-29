package com.example.semestralnezadanie.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.semestralnezadanie.R
import com.example.semestralnezadanie.databinding.FragmentLoginBinding
import com.example.semestralnezadanie.fragments.viewmodels.LoginRegisterViewModel
import com.example.semestralnezadanie.fragments.viewmodels.ViewModelHelper
import com.google.android.material.textfield.TextInputLayout


class LoginFragment : Fragment()
{
    private lateinit var binding : FragmentLoginBinding
    private lateinit var loginRegisterViewModel : LoginRegisterViewModel
    private lateinit var username : TextInputLayout
    private lateinit var password : TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        loginRegisterViewModel = ViewModelProvider(this, ViewModelHelper.provideUserViewModelFactory(requireContext()))[LoginRegisterViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        username = view.findViewById(R.id.usernameField)
        password = view.findViewById(R.id.passwordField)

        binding.apply { }

        binding.loginBtn.setOnClickListener{
            /*
                TODO:
                 1. overit ci su policka vyplnene
                 2. odoslat request na prihlasenie (zapamatat si access a refresh token)
                 3. status kod 200 - automaticky prihlasi pouzivatela
                 4. status kod iny - vypise chybu
             */
            checkLoginFields()
        }

        binding.registerBtn.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegistrationFragment()
            view.findNavController().navigate(action)
        }
    }

    private fun checkLoginFields() : Boolean
    {
        if(username.editText?.text.toString().isEmpty())
        {
            username.editText?.error = "Používateľské meno musí byť zadané"
            return false
        }

        if(password.editText?.text.toString().isEmpty())
        {
            password.editText?.error = "Heslo musí byť zadané"
            return false
        }

        return true
    }
}