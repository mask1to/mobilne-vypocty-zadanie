package com.example.semestralnezadanie.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
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
    }

    override fun onClick(p0: View?)
    {
        TODO("Not yet implemented")
    }
}