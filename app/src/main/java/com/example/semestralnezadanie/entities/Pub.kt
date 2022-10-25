package com.example.semestralnezadanie.entities

data class Pub(
    val name : String,
    val amenity : String,
    val latitude : Float,
    val longitude : Float,
    val opening_hours : String,
    val website : String,
    val contactPhone : String
)