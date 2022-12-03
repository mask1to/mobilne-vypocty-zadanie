package com.example.semestralnezadanie.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.LottieAnimationView
import com.example.semestralnezadanie.R
import com.example.semestralnezadanie.database.preferences.Preferences
import com.example.semestralnezadanie.databinding.FragmentInfoBinding
import com.example.semestralnezadanie.databinding.RecyclerFragmentBinding
import com.example.semestralnezadanie.fragments.viewmodels.InfoViewModel
import com.example.semestralnezadanie.fragments.viewmodels.PubsViewModel
import com.example.semestralnezadanie.fragments.viewmodels.ViewModelHelper


class InfoFragment : Fragment()
{
    private var _binding : FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private val args : InfoFragmentArgs by navArgs()


    private lateinit var txtName : TextView
    private lateinit var txtOpening : TextView
    private lateinit var txtSmoking : TextView
    private lateinit var txtUsers : TextView
    private lateinit var txtTakeAway : TextView

    private lateinit var openingHrsTitle : TextView
    private lateinit var takeAwayTitle : TextView
    private lateinit var smokingTitle : TextView
    private lateinit var pubUsersTitle : TextView

    private lateinit var mapButton : Button
    private lateinit var webButton : Button
    private lateinit var callButton : Button
    private lateinit var phoneContact : String
    private lateinit var website : String


    private val infoViewModel : InfoViewModel by lazy {
        ViewModelProvider(this, ViewModelHelper.providePubViewModelFactory(requireContext()))[InfoViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View
    {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        txtName = binding.pubNameInfo
        txtOpening = binding.openingHrsTxt
        txtSmoking = binding.smokingTxt
        txtUsers = binding.userCountTxt
        txtTakeAway = binding.takeAwayTxt

        openingHrsTitle = binding.openingHrsTitle
        takeAwayTitle = binding.takeAwayTitle
        smokingTitle = binding.smokingTitle
        pubUsersTitle = binding.pubUsersTitle

        val preferences = Preferences.getInstance().getUserItem(requireContext())
        if((preferences?.userId ?: "").isBlank())
        {
            val action = InfoFragmentDirections.actionInfoFragmentToLoginFragment()
            Navigation.findNavController(view).navigate(action)
            return
        }
        mapButton = binding.mapBtn
        webButton = binding.webBtn
        callButton = binding.callBtn


        binding.apply {
            infomodel = infoViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        infoViewModel.pub.observe(viewLifecycleOwner)
        {
            it?.let {
                txtName.text = it.pubName
                if(!it.tags["opening_hours"].isNullOrEmpty())
                {
                    txtOpening.text = it.tags["opening_hours"].toString()
                }
                else
                {
                    txtOpening.text = "Nemá otváracie hodiny"
                }

                if(!it.tags["smoking"].isNullOrEmpty())
                {
                    if(it.tags["smoking"].toString() == "yes")
                    {
                        txtSmoking.text = "áno"
                    }
                    else
                    {
                        txtSmoking.text = "nie"
                    }
                }
                else
                {
                    txtSmoking.text = "Informácia nedostupná"
                }

                if(!it.tags["takeaway"].isNullOrEmpty())
                {
                    if(it.tags["takeaway"].toString() == "yes")
                    {
                        txtTakeAway.text = "áno"
                    }
                    else
                    {
                        txtTakeAway.text = "nie"
                    }
                }
                else
                {
                    txtTakeAway.text = "Informácia nedostupná"
                }

                if(!it.tags["website"].isNullOrEmpty())
                {
                    website = it.tags["website"].toString()
                }
                else
                {
                    webButton.isEnabled = false
                }
                if(!it.tags["phone"].isNullOrEmpty())
                {
                    phoneContact = it.tags["phone"].toString()
                }
                else
                {
                    callButton.isEnabled = false
                }
            }
        }

        mapButton.setOnClickListener {
            val uri =
                "http://maps.google.com/maps?q=loc:${infoViewModel.pub.value?.latitude},${infoViewModel.pub.value?.longitude}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }

        webButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(website))
            startActivity(intent)
        }

        callButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneContact"))
            startActivity(intent)
        }

        infoViewModel.loadPubs(args.pubId)
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