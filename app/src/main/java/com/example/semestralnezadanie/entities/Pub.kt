package com.example.semestralnezadanie.entities

import com.example.semestralnezadanie.database.pubs.PubsModel

data class Pub(
    val id : Long = 0,
    val name : String?,
    val amenity : String,
    val owner : String?,
    val latitude : String,
    val longitude : String,
    val website : String?,
    val contactPhone : String?,
    val users : Int
){
    fun asDatabaseModel():PubsModel{
        return PubsModel(
            id = id,
            name = name,
            type = amenity,
            owner = owner,
            latitude = latitude,
            longitude = longitude,
            website = website,
            phoneContact = contactPhone,
            users = users
        )
    }
}
