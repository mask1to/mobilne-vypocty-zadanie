package com.example.semestralnezadanie.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.semestralnezadanie.R

class FormFragment : Fragment(), View.OnClickListener,
    ActivityCompat.OnRequestPermissionsResultCallback
{

    private lateinit var nameInput : EditText
    private lateinit var companyName : EditText
    private lateinit var companyLatitude : EditText
    private lateinit var companyLongitude : EditText
    private lateinit var confirmationBtn : Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        parentFragment?.activity?.actionBar?.hide()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.form_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        nameInput = view.findViewById(R.id.nameTxt)
        companyName = view.findViewById(R.id.companyName)
        companyLatitude = view.findViewById(R.id.companyLatitude)
        companyLongitude = view.findViewById(R.id.companyLongitude)
        val nameInputTxt = nameInput.text.toString()
        val companyNameTxt = companyName.text.toString()
        val companyLatitudeFloat = companyLatitude.text.toString()
        val companyLongitudeFloat = companyLongitude.text.toString()

        view.findViewById<View>(R.id.confirmationBtn).setOnClickListener {
            val action = FormFragmentDirections.actionFormFragmentToFetchFragment(nameInputTxt, companyNameTxt, companyLatitudeFloat, companyLongitudeFloat)
            view.findNavController().navigate(action)
        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}