package com.example.semestralnezadanie.entities

data class User(
    val id : Long = 0,
    val username : String?,
    val password : String,
    val accessToken : String,
    val refreshToken : String
)