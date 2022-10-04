package com.example.semestralnezadanie.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.semestralnezadanie.R

class IntroFragment : Fragment(), View.OnClickListener,
    ActivityCompat.OnRequestPermissionsResultCallback
{
    private lateinit var formButton : Button

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val animationView: LottieAnimationView = view.findViewById(R.id.animation_view)

        setupAnimation(animationView)

        formButton = view.findViewById(R.id.formBtn)
        view.findViewById<View>(R.id.formBtn).setOnClickListener {
            val action = IntroFragmentDirections.actionIntroFragmentToFormFragment()
            view.findNavController().navigate(action)
        }
    }

    override fun onClick(p0: View)
    {

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