package com.example.semestralnezadanie.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.semestralnezadanie.R
import com.example.semestralnezadanie.database.pubs.PubsModel
import com.example.semestralnezadanie.fragments.RecyclerFragmentDirections

class PubAdapter(val pubContext : Context): RecyclerView.Adapter<PubAdapter.PubViewHolder>()
{
    var data : List<PubsModel> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    set(value){
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PubViewHolder
    {
        return PubViewHolder(parent)
    }

    override fun onBindViewHolder(holder: PubViewHolder, position: Int)
    {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int
    {
        return data.size
    }

    class PubViewHolder(private val parent : ViewGroup, itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)) : RecyclerView.ViewHolder(itemView)
    {
        fun bind(item : PubsModel)
        {
            itemView.findViewById<TextView>(R.id.nameText).text = item.name
            itemView.findViewById<TextView>(R.id.amenityTxt).text = item.type
            itemView.findViewById<TextView>(R.id.pubUsersTxt).text = item.users.toString()
            itemView.findViewById<CardView>(R.id.listCardView).setOnClickListener {
                val action = RecyclerFragmentDirections.actionRecyclerFragmentToInfoFragment(item.id.toString())
                Navigation.findNavController(itemView).navigate(action)
            }
        }
    }

}