package com.example.semestralnezadanie.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.semestralnezadanie.Pub
import com.example.semestralnezadanie.Pubs
import com.example.semestralnezadanie.R

class PubAdapter(private val pubsList : Pubs): RecyclerView.Adapter<PubAdapter.PubViewHolder>()
{
    var onItemClick : ((Pub) -> Unit)? = null

    class PubViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val textView : TextView = itemView.findViewById(R.id.nameText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PubViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return PubViewHolder(view)
    }

    override fun onBindViewHolder(holder: PubViewHolder, position: Int)
    {
        val textView = pubsList.elements.get(position)
        holder.textView.text = textView.tags.name
        //maybe listener add
    }

    override fun getItemCount(): Int
    {
        return pubsList.elements.size
    }
}