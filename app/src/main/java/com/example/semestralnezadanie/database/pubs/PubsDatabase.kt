package com.example.semestralnezadanie.database.pubs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.semestralnezadanie.entities.Pub

@Entity(tableName = "pubs")
data class PubsDatabase(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    @ColumnInfo(name = "name")
    val name : String?,
    @ColumnInfo(name = "type")
    val type : String,
    @ColumnInfo(name = "firm_owner")
    val owner : String?,
    @ColumnInfo(name = "latitude")
    val latitude : String,
    @ColumnInfo(name = "longitude")
    val longitude : String,
    @ColumnInfo(name = "website")
    val website : String?,
    @ColumnInfo(name = "phone_contact")
    val phoneContact : String?,
)

fun PubsDatabase.asDomainModel():Pub{
    return Pub(
        id = id,
        name = name,
        amenity = type,
        latitude = latitude,
        longitude = longitude,
        owner = owner,
        website = website,
        contactPhone = phoneContact
    )
}

fun List<PubsDatabase>.asDomainModel():List<Pub>{
    return map{
        it.asDomainModel()
    }
}
