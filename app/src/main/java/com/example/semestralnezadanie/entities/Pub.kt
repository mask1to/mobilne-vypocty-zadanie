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
)

data class PubDetail(val key : String, val value : String){}

data class CurrentLocation(
    val latitude: Double,
    val longitude: Double
)
