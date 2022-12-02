package com.example.semestralnezadanie.api

import com.google.gson.annotations.SerializedName

data class PubGeneralResponse(
    @SerializedName("bar_id")
    val pubId : String,
    @SerializedName("bar_name")
    val pubName : String,
    @SerializedName("bar_type")
    val pubAmenity : String,
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double,
    var users : Int
)

data class PubDetailItemResponse(
    val id : String,
    @SerializedName("lat")
    val latitude : Double,
    @SerializedName("lon")
    val longitude : Double,
    @SerializedName("type")
    val amenity : String,
    val tags : Map<String, String>
)

data class PubDetailResponse(
    val elements : List<PubDetailItemResponse>
)

data class PubMessageRequest(
    @SerializedName("id")
    val pubId: String,
    @SerializedName("name")
    val pubName: String,
    @SerializedName("type")
    val pubAmenity: String,
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double
)