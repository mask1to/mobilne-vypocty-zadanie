package com.example.semestralnezadanie.database.pubs

import android.location.Location
import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.semestralnezadanie.entities.UserCurrentLocation

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



}


