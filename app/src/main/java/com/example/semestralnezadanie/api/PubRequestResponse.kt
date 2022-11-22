package com.example.semestralnezadanie.api

data class PubGeneralResponse(
    val pubId : String,
    val pubName : String,
    val pubAmenity : String,
    val latitude: Double,
    val longitude: Double,
    var users : Int
)

data class PubDetailItemResponse(
    val id : String,
    val latitude : Double,
    val longitude : Double,
    val amenity : String,
    val tags : Map<String, String>
)

data class PubDetailResponse(
    val elements : List<PubDetailItemResponse>
)

data class PubMessageRequest(
    val pubId: String,
    val pubName: String,
    val pubAmenity: String,
    val latitude: Double,
    val longitude: Double
)