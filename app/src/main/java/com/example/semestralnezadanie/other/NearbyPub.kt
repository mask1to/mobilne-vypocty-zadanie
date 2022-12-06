package com.example.semestralnezadanie.other

import android.location.Location

class NearbyPub(
    val pubId : String,
    var pubName : String,
    val pubAmenity : String,
    val lat : Double,
    val lon : Double,
    val tags : Map<String, String>,
    var users : Int = 0,
    var distance : Double = 0.0)
{

    fun distanceTo(location: UserCurrentLocation): Double
    {
        return Location("").apply {
            latitude=lat
            longitude=lon
        }.distanceTo(Location("").apply {
            latitude=location.userLatitude
            longitude=location.userLongitude
        }).toDouble()
    }


}