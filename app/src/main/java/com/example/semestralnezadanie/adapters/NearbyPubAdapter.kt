package com.example.semestralnezadanie.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.semestralnezadanie.R
import com.example.semestralnezadanie.other.NearbyPub
import com.example.semestralnezadanie.fragments.viewmodels.LocationViewModel

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
        holder.distanceLocation.text = "%.2f m".format(item.distance)
        holder.cardViewLocationPub.setOnClickListener {
            locationViewModel.userPub.value = item
        }

    }

    override fun getItemCount(): Int
    {
        return data.size
    }
}