package com.example.semestralnezadanie.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.semestralnezadanie.R
import com.google.android.material.textfield.TextInputLayout


class LoginFragment : Fragment()
{
    private lateinit var username : TextInputLayout
    private lateinit var password : TextInputLayout
    private lateinit var loginButton : Button
    private lateinit var registerButton : Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        username = view.findViewById(R.id.usernameField)
        password = view.findViewById(R.id.passwordField)
        loginButton = view.findViewById(R.id.loginBtn)
        registerButton = view.findViewById(R.id.registerBtn)

        loginButton.setOnClickListener {
            /*
                TODO:
                 1. overit ci su policka vyplnene
                 2. odoslat request na prihlasenie (zapamatat si access a refresh token)
                 3. status kod 200 - automaticky prihlasi pouzivatela
                 4. status kod iny - vypise chybu
             */
            checkLoginFields()
        }

        registerButton.setOnClickListener {
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