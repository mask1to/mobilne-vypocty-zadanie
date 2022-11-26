package com.example.semestralnezadanie.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.semestralnezadanie.R
import com.example.semestralnezadanie.entities.Pub
import com.example.semestralnezadanie.fragments.RecyclerFragmentDirections
import com.example.semestralnezadanie.fragments.RecyclerFragment.Companion.isSorted

class PubAdapter(private val context : Context): RecyclerView.Adapter<PubAdapter.PubViewHolder>()
{
    var data : List<Pub> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    set(value){
        field = value
        notifyDataSetChanged()
    }

    class PubViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val cardView : CardView = itemView.findViewById(R.id.listCardView)
        val pubNameTextView : TextView = itemView.findViewById(R.id.nameText)
        val amenityTextView : TextView = itemView.findViewById(R.id.amenityTxt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PubViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return PubViewHolder(view)
    }

    override fun onBindViewHolder(holder: PubViewHolder, position: Int)
    {
        val pub = data[position]
        holder.pubNameTextView.text = pub.name
        holder.amenityTextView.text = pub.amenity
        //maybe listener add
        holder.cardView.setOnClickListener {
            val action = RecyclerFragmentDirections.actionRecyclerFragmentToInfoFragment()
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int
    {
        return data.size
    }
}