package com.example.semestralnezadanie.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.semestralnezadanie.R


class FetchFragment: Fragment()
{
    private lateinit var txtName : TextView
    private lateinit var txtPubType : TextView
    private lateinit var animationView: LottieAnimationView
    private lateinit var showOnMapBtn : Button

    private lateinit var pubName : String
    private lateinit var contactPhone : String
    private lateinit var website : String
    private lateinit var  openingHours : String
    private var newLatitude : Float = 0.0f
    private var newLongitude : Float = 0.0f

    companion object {
        const val NAME = "name"
        const val PUB = "pub_name"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val PHONE = "phone"
        const val WEBSITE = "website"
        const val OPENING_HOURS = "opening_hours"
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        parentFragment?.activity?.actionBar?.hide()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fetch_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        val safeArgs : FetchFragmentArgs by navArgs()

        val theName = safeArgs.setName
        val typeOfPub = safeArgs.setTyp
        val companyLatitude = safeArgs.setLatitude
        val companyLongitude = safeArgs.setLongitude

        txtName = view.findViewById(R.id.fetchName)
        txtPubType = view.findViewById(R.id.fetchType)

        txtName.text = theName
        txtPubType.text = typeOfPub

        if (!companyLatitude.isEmpty())
        {
            newLatitude = companyLatitude.toFloat()
        }
        if(!companyLongitude.isEmpty())
        {
            newLongitude = companyLongitude.toFloat()
        }

        view.findViewById<View>(R.id.showOnMapBtn).setOnClickListener {
            val uri =
                "http://maps.google.com/maps?q=loc:$newLatitude,$newLongitude"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @SuppressLint("Range")
    private fun setupAnimation(animationView: LottieAnimationView)
    {
        animationView.speed = 1.0F // How fast does the animation play
        animationView.progress = 50F // Starts the animation from 50% of the beginning
        animationView.addAnimatorUpdateListener {
            // Called every time the frame of the animation changes
        }
        animationView.repeatMode = LottieDrawable.RESTART // Restarts the animation (you can choose to reverse it as well)
        animationView.cancelAnimation()
    }
}