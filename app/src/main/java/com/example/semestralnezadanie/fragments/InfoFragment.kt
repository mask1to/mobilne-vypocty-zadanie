package com.example.semestralnezadanie.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.LottieAnimationView
import com.example.semestralnezadanie.R
import com.example.semestralnezadanie.data.DataProvider
import com.example.semestralnezadanie.entities.Pub
import com.example.semestralnezadanie.entities.allPubs


class InfoFragment : Fragment()
{
    private lateinit var phoneContact : String
    private lateinit var website : String
    private var latitude : Float? = null
    private var longitude : Float? = null

    private lateinit var txtName : TextView
    private lateinit var txtOpeningHrs : TextView
    private lateinit var mapButton : Button
    private lateinit var webButton : Button
    private lateinit var callButton : Button
    private lateinit var deleteButton : Button


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        txtName = view.findViewById(R.id.pubNameInfo)
        txtOpeningHrs = view.findViewById(R.id.openingHrsInfo)
        mapButton = view.findViewById(R.id.mapBtn)
        webButton = view.findViewById(R.id.webBtn)
        callButton = view.findViewById(R.id.callBtn)
        deleteButton = view.findViewById(R.id.deleteBtn)

        val safeArgs : InfoFragmentArgs by navArgs()
        txtName.text = safeArgs.pubClass.name
        txtOpeningHrs.text = safeArgs.pubClass.opening_hours
        website = safeArgs.pubClass.website
        phoneContact = safeArgs.pubClass.contactPhone

        if(latitude.toString().isEmpty() || longitude.toString().isEmpty())
        {
            Toast.makeText(context, "No latitude or longitude", Toast.LENGTH_SHORT)
        }
        else
        {
            mapButton.setOnClickListener {
                val uri =
                    "http://maps.google.com/maps?q=loc:$latitude,$longitude"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(intent)
            }
        }

        deleteButton.setOnClickListener {
            allPubs.remove(safeArgs.pubClass)
            val action = InfoFragmentDirections.actionInfoFragmentToRecyclerFragment()
            findNavController().navigate(action)
        }

        if(website.isEmpty())
        {
            Toast.makeText(context, "No website", Toast.LENGTH_SHORT)
        }
        else
        {
            webButton.setOnClickListener {
                Log.d("web", website.toString())
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(website))
                startActivity(intent)
            }
        }

        if(phoneContact.isEmpty())
        {
            Toast.makeText(context, "No phone contact", Toast.LENGTH_SHORT)
        }
        else
        {
            callButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneContact"))
                startActivity(intent)
            }
        }

    }

    @SuppressLint("Range")
    private fun setupAnimation(animationView: LottieAnimationView)
    {
        animationView.speed = 1.0F // How fast does the animation play
        animationView.progress = 50F // Starts the animation from 50% of the beginning
        animationView.setAnimation(R.raw.lemonade)
        animationView.setOnClickListener {
            animationView.playAnimation()
        }
    }
}