package com.example.semestralnezadanie.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.semestralnezadanie.R
import com.example.semestralnezadanie.database.preferences.Preferences
import com.example.semestralnezadanie.databinding.FragmentRegistrationBinding
import com.example.semestralnezadanie.fragments.viewmodels.LoginRegisterViewModel
import com.example.semestralnezadanie.fragments.viewmodels.ViewModelHelper
import com.google.android.material.textfield.TextInputLayout


class RegistrationFragment : Fragment()
{

    private var _binding : FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private lateinit var usernameInput : TextInputLayout
    private lateinit var firstPasswordInput : TextInputLayout
    private lateinit var secondPasswordInput : TextInputLayout

    private val loginRegisterViewModel : LoginRegisterViewModel by lazy {
        ViewModelProvider(this, ViewModelHelper.provideUserViewModelFactory(requireContext()))[LoginRegisterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View
    {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        usernameInput = binding.usernameFieldRegister
        firstPasswordInput = binding.passwordRegisterField
        secondPasswordInput = binding.passwordRegisterField2

        val preferences = Preferences.getInstance().getUserItem(requireContext())
        if((preferences?.userId ?: "").isNotBlank())
        {
            val action = RegistrationFragmentDirections.actionRegistrationFragmentToRecyclerFragment()
            Navigation.findNavController(view).navigate(action)
            return
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            loginregistermodel = loginRegisterViewModel
        }


        /**
         * Buttons
         */

        binding.loginBtnOnRegister.setOnClickListener {
            val action = RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment()
            Navigation.findNavController(view).navigate(action)
        }

        binding.registerBtn2.setOnClickListener {
            checkAllFields()
            val action = RegistrationFragmentDirections.actionRegistrationFragmentToRecyclerFragment()
            Navigation.findNavController(view).navigate(action)
            /*TODO:
                1. Overenie ci su policka vyplnene
                2. Overenie zhody hesiel
                3. Odoslanie requestu na registraciu
                4. Status kod 200 - automaticky prihlasit pouzivatela, ak iny kod - vypisat chybu
            * */
        }


        loginRegisterViewModel.userResponse.observe(viewLifecycleOwner)
        {
            it?.let {
                Preferences.getInstance().applyUserItem(requireContext(), it)
                val action = RegistrationFragmentDirections.actionRegistrationFragmentToRecyclerFragment()
                Navigation.findNavController(requireView()).navigate(action)
            }
        }

    }

    private fun checkAllFields() : Boolean
    {
        if(usernameInput.editText?.text.toString().isEmpty())
        {
            usernameInput.editText?.error = "Používateľské meno musí byť vyplnené"
            return false
        }

        if(firstPasswordInput.editText?.text.toString().isEmpty() || secondPasswordInput.editText?.text.toString().isEmpty())
        {
            firstPasswordInput.editText?.error = "Heslo musí byť zadané"
            return false
        }
        else if(firstPasswordInput.editText?.text.toString().length < 8 || secondPasswordInput.editText?.text.toString().length < 8)
        {
            firstPasswordInput.editText?.error = "Heslo musí mať aspoň 8 znakov"
            return false
        }

        if(firstPasswordInput.editText?.text.toString() != secondPasswordInput.editText?.text.toString())
        {
            firstPasswordInput.editText?.error = "Heslá sa nezhodujú"
            return false
        }

        loginRegisterViewModel.signUp(usernameInput.editText?.text.toString(), firstPasswordInput.editText?.text.toString())
        return true
    }
}