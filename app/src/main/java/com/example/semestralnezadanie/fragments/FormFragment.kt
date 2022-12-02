package com.example.semestralnezadanie.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.semestralnezadanie.R

class FormFragment : Fragment(),
    ActivityCompat.OnRequestPermissionsResultCallback
{

    private lateinit var nameInput : EditText
    private lateinit var typeOfCompany : EditText
    private lateinit var companyLatitude : EditText
    private lateinit var companyLongitude : EditText
    private lateinit var websitePub : EditText
    private lateinit var phoneContact : EditText

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
        typeOfCompany = view.findViewById(R.id.pubType)
        websitePub = view.findViewById(R.id.pubWebsite)
        phoneContact = view.findViewById(R.id.pubPhone)
        companyLatitude = view.findViewById(R.id.companyLatitude)
        companyLongitude = view.findViewById(R.id.companyLongitude)

        view.findViewById<View>(R.id.confirmationBtn).setOnClickListener {
            val nameInputTxt = nameInput.text.toString()
            val typeOfCompanyTxt = typeOfCompany.text.toString()
            val websiteTxt = websitePub.text.toString()
            val phoneTxt = phoneContact.text.toString()
            val companyLatitudeFloat = companyLatitude.text.toString()
            val companyLongitudeFloat = companyLongitude.text.toString()

            /*val action = FormFragmentDirections.actionFormFragmentToFetchFragment(nameInputTxt, typeOfCompanyTxt, companyLatitudeFloat, companyLongitudeFloat, websiteTxt, phoneTxt)
            view.findNavController().navigate(action)*/
        }
    }

}