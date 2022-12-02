package com.example.semestralnezadanie.api

import com.google.gson.annotations.SerializedName

data class UserGeneralResponse(
    @SerializedName("uid")
    val userId : String,
    @SerializedName("refresh")
    val refreshToken : String,
    @SerializedName("access")
    val accessToken : String
)

data class UserLoginRequest(
    @SerializedName("name")
    val userName : String,
    @SerializedName("password")
    val userPassword : String
)

data class UserRefresh(
    @SerializedName("refresh")
    val refreshToken : String
)

data class UserRequest(
    @SerializedName("name")
    val userName : String,
    @SerializedName("password")
    val userPassword: String
)