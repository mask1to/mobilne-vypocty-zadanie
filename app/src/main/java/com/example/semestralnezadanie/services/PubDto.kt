package com.example.semestralnezadanie.services

import com.example.semestralnezadanie.database.pubs.PubsModel
import com.example.semestralnezadanie.entities.Pub
import com.squareup.moshi.Json

data class PubContainerDto(
    @Json(name = "documents") val pubList: List<PubDto>
)

data class PubDto(
    @Json(name = "id") val id: Long,
    @Json(name = "lat") val latitude: String,
    @Json(name = "lon") val longitude: String,
    @Json(name = "tags") val tags: PubDtoTags,
)

data class PubDtoTags(
    @Json(name = "name")
    val name: String?,
    @Json(name = "amenity")
    val type: String,
    @Json(name = "operator")
    val ownerName: String?,
    @Json(name = "phone")
    val phoneNumber: String?,
    @Json(name = "website")
    val webPage: String?,
)
