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
import com.example.semestralnezadanie.database.friends.FriendsModel
import com.example.semestralnezadanie.fragments.FriendsFragmentDirections

class FriendAdapter(val friendContext : Context): RecyclerView.Adapter<FriendAdapter.FriendViewHolder>()
{
    var data : List<FriendsModel> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder
    {
        return FriendViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int)
    {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int
    {
        return data.size
    }

    class FriendViewHolder(private val parent : ViewGroup, itemView : View = LayoutInflater.from(parent.context).inflate(
        R.layout.friend_item, parent, false)) : RecyclerView.ViewHolder(itemView)
    {
        fun bind(item : FriendsModel)
        {
            itemView.findViewById<TextView>(R.id.friendNameText).text = item.userName
            itemView.findViewById<TextView>(R.id.pubInfoTxt).text = item.pubName
            /*itemView.findViewById<TextView>(R.id.amenityTxt).text = item.type
            itemView.findViewById<TextView>(R.id.pubUsersTxt).text = item.users.toString()*/
            itemView.findViewById<CardView>(R.id.friendCardView).setOnClickListener {
                if(item.pubId.isNullOrEmpty())
                {

                }
                else
                {
                    val action = FriendsFragmentDirections.actionFriendsFragmentToInfoFragment(item.pubId.toString())
                    Navigation.findNavController(itemView).navigate(action)
                }
            }
        }
    }
}