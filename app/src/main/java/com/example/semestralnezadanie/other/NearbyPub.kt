package com.example.semestralnezadanie.other

import android.location.Location

class NearbyPub(
    val pubId : String,
    var pubName : String,
    val pubAmenity : String,
    val lat : Double,
    val lon : Double,
    val tags : Map<String, String>,
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NearbyPub

        if (pubId != other.pubId) return false
        if (pubName != other.pubName) return false
        if (pubAmenity != other.pubAmenity) return false
        if (lat != other.lat) return false
        if (lon != other.lon) return false
        if (tags != other.tags) return false
        if (distance != other.distance) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pubId.hashCode()
        result = 31 * result + pubName.hashCode()
        result = 31 * result + pubAmenity.hashCode()
        result = 31 * result + lat.hashCode()
        result = 31 * result + lon.hashCode()
        result = 31 * result + tags.hashCode()
        result = 31 * result + distance.hashCode()
        return result
    }

    override fun toString(): String {
        return "NearbyPub(pubId='$pubId', pubName='$pubName', pubAmenity='$pubAmenity', lat=$lat, lon=$lon, tags=$tags, distance=$distance)"
    }
}