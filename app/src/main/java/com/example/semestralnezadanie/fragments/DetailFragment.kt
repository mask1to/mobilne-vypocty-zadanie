package com.example.semestralnezadanie.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.semestralnezadanie.R


class DetailFragment : Fragment()
{
    private lateinit var name : String
    private lateinit var pubName : String
    private var latitude : Float? = null
    private var longitude : Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            name = it.getString(NAME).toString()
            pubName = it.getString(PUB).toString()
            latitude = it.getFloat(LATITUDE)
            longitude = it.getFloat(LONGITUDE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    companion object {
        const val NAME = "name"
        const val PUB = "pub_name"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val PHONE = "phone"
        const val WEBSITE = "website"
        const val OPENING_HOURS = "opening_hours"
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

    //showOnMap TODO

}