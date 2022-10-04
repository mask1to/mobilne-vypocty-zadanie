package com.example.semestralnezadanie.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.semestralnezadanie.R

class FetchFragment: Fragment(), View.OnClickListener,
    ActivityCompat.OnRequestPermissionsResultCallback
{

    private lateinit var txtName : TextView
    private lateinit var txtCompany : TextView
    private lateinit var animationView: LottieAnimationView
    private lateinit var showOnMapBtn : Button

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

       //animationView = view.findViewById(R.id.animation_view)
        //setupAnimation(animationView)

        val safeArgs : FetchFragmentArgs by navArgs()
        val theName = safeArgs.setName
        val companyName = safeArgs.setCompanyName
        val companyLatitude = safeArgs.setLatitude
        val companyLongitude = safeArgs.setLongitude

        Log.d("theName: ", theName.toString())
        Log.d("companyName: ", companyName.toString())

        txtName = view.findViewById(R.id.fetchName)
        txtCompany = view.findViewById(R.id.fetchCompanyName)

        //txtName.text = theName
        //txtCompany.text = companyName

        view.findViewById<View>(R.id.showOnMapBtn).setOnClickListener {

        }

    }

    override fun onClick(p0: View?)
    {
        TODO("Not yet implemented")
    }

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