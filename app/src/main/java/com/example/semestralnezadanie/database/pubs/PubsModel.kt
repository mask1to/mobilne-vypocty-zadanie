package com.example.semestralnezadanie.database.pubs

import android.location.Location
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.semestralnezadanie.other.UserCurrentLocation

@Entity(tableName = "pubs")
class PubsModel(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    @ColumnInfo(name = "name")
    val name : String?,
    @ColumnInfo(name = "type")
    val type : String,
    @ColumnInfo(name = "latitude")
    val lat : Double,
    @ColumnInfo(name = "longitude")
    val lon : Double,
    @ColumnInfo(name = "users_count")
    val users : Int,
    @ColumnInfo(name = "distance")
    var distance : Double = 0.0
)
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

        other as PubsModel

        if (id != other.id) return false
        if (name != other.name) return false
        if (type != other.type) return false
        if (lat != other.lat) return false
        if (lon != other.lon) return false
        if (users != other.users) return false
        if (distance != other.distance) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + type.hashCode()
        result = 31 * result + lat.hashCode()
        result = 31 * result + lon.hashCode()
        result = 31 * result + users
        result = 31 * result + distance.hashCode()
        return result
    }

    override fun toString(): String {
        return "PubsModel(id=$id, name=$name, type='$type', lat=$lat, lon=$lon, users=$users, distance=$distance)"
    }

}


