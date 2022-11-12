package com.example.semestralnezadanie.entities

import android.content.Context
import android.os.Parcelable
import com.example.semestralnezadanie.R
import com.example.semestralnezadanie.database.DatabasePubs
import com.example.semestralnezadanie.database.DatabaseUsers
import com.example.semestralnezadanie.database.pubs.PubsDatabase
import kotlinx.parcelize.Parcelize
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

data class Pub(
    val id : Long = 0,
    val name : String?,
    val amenity : String,
    val owner : String?,
    val latitude : String,
    val longitude : String,
    val website : String?,
    val contactPhone : String?
){
    fun asDatabaseModel():PubsDatabase{
        return PubsDatabase(
            id = id,
            name = name,
            type = amenity,
            owner = owner,
            latitude = latitude,
            longitude = longitude,
            website = website,
            phoneContact = contactPhone
        )
    }
}
