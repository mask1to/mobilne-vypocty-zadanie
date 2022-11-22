package com.example.semestralnezadanie.api

data class UserGeneralResponse(
    val userId : String,
    val refreshToken : String,
    val accessToken : String
)

data class UserLoginRequest(
    val userName : String,
    val userPassword : String
)

data class UserRefresh(
    val refreshToken : String
)

data class UserRequest(
    val userName : String,
    val userPassword: String
)