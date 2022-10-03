package com.example.semestralnezadanie.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.semestralnezadanie.R

class IntroFragment : Fragment(), View.OnClickListener,
    ActivityCompat.OnRequestPermissionsResultCallback
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        parentFragment?.activity?.actionBar?.hide()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.intro_fragment, container, false)
    }

    override fun onClick(p0: View?) {

    }
}