package com.example.semestralnezadanie.database.firms

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pubs")
data class PubsDatabase(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    @ColumnInfo(name = "name")
    val name : String?,
    @ColumnInfo(name = "type")
    val type : String,
    @ColumnInfo(name = "firm_owner")
    val owner : String,
    @ColumnInfo(name = "latitude")
    val latitude : String,
    @ColumnInfo(name = "longitude")
    val longitude : String,
    @ColumnInfo(name = "phone_contact")
    val phoneContact : String,
    @ColumnInfo(name = "website")
    val website : String,
)
