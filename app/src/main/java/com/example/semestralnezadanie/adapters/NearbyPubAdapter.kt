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

class NearbyPubAdapter(val nearbyPubContext : Context): RecyclerView.Adapter<NearbyPubAdapter.NearbyPubViewHolder>()
{
    var data : List<NearbyPub> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value){
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearbyPubViewHolder
    {
        return NearbyPubViewHolder(parent)
    }

    override fun onBindViewHolder(holder: NearbyPubViewHolder, position: Int)
    {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int
    {
        return data.size
    }

    class NearbyPubViewHolder(private val parent : ViewGroup, itemView : View = LayoutInflater.from(parent.context).inflate(
        R.layout.nearby_item, parent, false)) : RecyclerView.ViewHolder(itemView)
    {
        private lateinit var locationViewModel : LocationViewModel

        fun bind(item : NearbyPub)
        {
            itemView.findViewById<TextView>(R.id.nameLocationTxt).text = item.pubName
            itemView.findViewById<TextView>(R.id.amenityLocationTxt).text = item.pubAmenity
            /*itemView.findViewById<TextView>(R.id.amenityTxt).text = item.type
            itemView.findViewById<TextView>(R.id.pubUsersTxt).text = item.users.toString()*/
            itemView.findViewById<CardView>(R.id.locationCardView).setOnClickListener {
                if(!item.pubId.isNullOrEmpty())
                {
                    /*val action = FriendsFragmentDirections.actionFriendsFragmentToInfoFragment(item.pubId.toString())
                    Navigation.findNavController(itemView).navigate(action)*/
                }
            }
        }
    }
}