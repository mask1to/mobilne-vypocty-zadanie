package com.example.semestralnezadanie.entities

import android.location.Location

class NearbyPub(
    val pubId : String,
    val pubName : String,
    val pubAmenity : String,
    val latitude : Double,
    val longitude : Double,
    val tags : Map<String, String>,
    var distance : Double = 0.0)
{
    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NearbyPub

        if (pubId != other.pubId) return false
        if (pubName != other.pubName) return false
        if (pubAmenity != other.pubAmenity) return false
        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false
        if (tags != other.tags) return false
        if (distance != other.distance) return false

        return true
    }

    override fun hashCode(): Int
    {
        var result = pubId.hashCode()
        result = 31 * result + pubName.hashCode()
        result = 31 * result + pubAmenity.hashCode()
        result = 31 * result + latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        result = 31 * result + tags.hashCode()
        result = 31 * result + distance.hashCode()
        return result
    }

    override fun toString(): String
    {
        return "NearbyPub(pubId='$pubId', pubName='$pubName', pubAmenity='$pubAmenity', latitude=$latitude, longitude=$longitude, tags=$tags, distance=$distance)"
    }


    fun getDistanceToPubNearby(currentLocation: CurrentLocation) : Double
    {
        val location = Location("").apply {
            latitude = latitude
            longitude = longitude
        }.distanceTo(Location("").apply {
            latitude = currentLocation.latitude
            longitude = currentLocation.longitude
        }).toDouble()

        return location
    }

}