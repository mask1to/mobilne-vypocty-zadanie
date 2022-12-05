package com.example.semestralnezadanie.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.semestralnezadanie.R
import com.example.semestralnezadanie.database.friends.FriendsModel
import com.example.semestralnezadanie.entities.NearbyPub
import com.example.semestralnezadanie.fragments.FriendsFragmentDirections
import com.example.semestralnezadanie.fragments.viewmodels.LocationViewModel
import com.example.semestralnezadanie.fragments.viewmodels.ViewModelHelper

class NearbyPubAdapter(val locationViewModel: LocationViewModel): RecyclerView.Adapter<NearbyPubAdapter.NearbyPubViewHolder>()
{
    var data : List<NearbyPub> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value){
            field = value
            notifyDataSetChanged()
        }

    class NearbyPubViewHolder(private val parent : ViewGroup, itemView : View = LayoutInflater.from(parent.context).inflate(
        R.layout.nearby_item, parent, false)) : RecyclerView.ViewHolder(itemView)
    {
        val nameLocationPub = itemView.findViewById<TextView>(R.id.nameLocationTxt)
        val amenityLocationPub = itemView.findViewById<TextView>(R.id.amenityLocationTxt)
        val cardViewLocationPub = itemView.findViewById<CardView>(R.id.locationCardView)
        val distanceLocation = itemView.findViewById<TextView>(R.id.distanceTxt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearbyPubViewHolder
    {
        return NearbyPubViewHolder(parent)
    }

    override fun onBindViewHolder(holder: NearbyPubViewHolder, position: Int)
    {
        val item = data[position]
        holder.nameLocationPub.text = item.pubName
        holder.amenityLocationPub.text = item.pubAmenity
        holder.distanceLocation.text = item.distance.toString()
        holder.cardViewLocationPub.setOnClickListener {
            locationViewModel.userPub.value = item
        }

    }

    override fun getItemCount(): Int
    {
        return data.size
    }
}