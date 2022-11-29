package com.example.semestralnezadanie.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.semestralnezadanie.R
import com.example.semestralnezadanie.databinding.FragmentRegistrationBinding
import com.example.semestralnezadanie.fragments.viewmodels.LoginRegisterViewModel
import com.google.android.material.textfield.TextInputLayout


class RegistrationFragment : Fragment()
{

    private lateinit var binding : FragmentRegistrationBinding
    private lateinit var loginRegisterViewModel : LoginRegisterViewModel
    private lateinit var usernameInput : TextInputLayout
    private lateinit var emailInput : TextInputLayout
    private lateinit var firstPasswordInput : TextInputLayout
    private lateinit var secondPasswordInput : TextInputLayout
    private lateinit var registerBtn : Button
    private lateinit var loginBtn : Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        loginRegisterViewModel = ViewModelProvider(this, )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        usernameInput = view.findViewById(R.id.usernameFieldRegister)
        emailInput = view.findViewById(R.id.emailRegisterField)
        firstPasswordInput = view.findViewById(R.id.passwordRegisterField)
        secondPasswordInput = view.findViewById(R.id.passwordRegisterField2)
        registerBtn = view.findViewById(R.id.registerBtn2)
        loginBtn = view.findViewById(R.id.loginBtnOnRegister)

        loginBtn.setOnClickListener {
            val action = RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment()
            view.findNavController().navigate(action)
        }

        registerBtn.setOnClickListener {
            /*TODO:
                1. Overenie ci su policka vyplnene
                2. Overenie zhody hesiel
                3. Odoslanie requestu na registraciu
                4. Status kod 200 - automaticky prihlasit pouzivatela, ak iny kod - vypisat chybu
            * */
            checkAllFields()
        }
    }

    private fun checkAllFields() : Boolean
    {
        if(usernameInput.editText?.text.toString().isEmpty())
        {
            usernameInput.editText?.error = "Používateľské meno musí byť vyplnené"
            return false
        }

        if(emailInput.editText?.text.toString().isEmpty())
        {
            emailInput.editText?.error = "E-mail musí byť vyplnený"
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
        }

        if(!firstPasswordInput.editText?.text.toString().equals(secondPasswordInput.editText?.text.toString()))
        {
            firstPasswordInput.editText?.error = "Heslá sa nezhodujú"
            return false
        }

        return true
    }
}