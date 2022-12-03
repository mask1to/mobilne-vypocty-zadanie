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

data class FriendsGeneralResponse(
    @SerializedName("user_id")
    val userId : String,
    @SerializedName("user_name")
    val userName : String,
    @SerializedName("bar_id")
    val pubId : String,
    @SerializedName("bar_name")
    val pubName : String,
    @SerializedName("time")
    val time : String,
    @SerializedName("bar_lat")
    val pubLatitude : Double,
    @SerializedName("bar_lon")
    val pubLongitude : Double
)

data class FriendContact(
    @SerializedName("contact")
    val contactName : String
)