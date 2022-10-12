package com.example.semestralnezadanie.adapters

import android.content.Context
import com.example.semestralnezadanie.R
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.semestralnezadanie.Pub
import com.example.semestralnezadanie.fragments.RecyclerFragment


class RecyclerAdapter(val context : Context, private val listRecyclerItem: ArrayList<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView
        val date: TextView

        init {
            name = itemView.findViewById(R.id.name)
            date = itemView.findViewById(R.id.date)
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): RecyclerView.ViewHolder {
        return when (i) {
            TYPE -> {
                val layoutView: View = LayoutInflater.from(viewGroup.context).inflate(
                    R.layout.list_item, viewGroup, false
                )
                ItemViewHolder(layoutView)
            }
            else -> {
                val layoutView: View = LayoutInflater.from(viewGroup.context).inflate(
                    R.layout.list_item, viewGroup, false
                )
                ItemViewHolder(layoutView)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        val viewType = getItemViewType(i)
        when (viewType) {
            TYPE -> {
                val itemViewHolder = viewHolder as ItemViewHolder
                val holidays: Pub = listRecyclerItem[i] as Pub
                itemViewHolder.name.text = holidays.getName()
                itemViewHolder.date.text = holidays.getDate()
            }
            else -> {
                val itemViewHolder = viewHolder as ItemViewHolder
                val holidays: Pub = listRecyclerItem[i] as Pub
                itemViewHolder.name.text = holidays.getName()
                itemViewHolder.date.text = holidays.getDate()
            }
        }
    }

    override fun getItemCount(): Int {
        return listRecyclerItem.size
    }

    companion object {
        private const val TYPE = 1
    }
}