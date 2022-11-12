package com.example.semestralnezadanie.services

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

private const val URL = "https://data.mongodb-api.com/app/data-fswjp/endpoint/data/v1/action/find"

data class PubService(
    @SerializedName("collection") val collection : String = "bars",
    @SerializedName("database") val database : String = "mobvapp",
    @SerializedName("dataSource") val dataSource : String = "Cluster0"
)

private val moshiBuilder = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit.Builder().addConverterFactory(
    MoshiConverterFactory.create(
    moshiBuilder))
    .baseUrl(URL)
    .build()

interface PubRestApi{
    @Headers(
        "Content-Type: application/json",
        "Access-Control-Request-Headers: *",
        "api-key: KHUu1Fo8042UwzczKz9nNeuVOsg2T4ClIfhndD2Su0G0LHHCBf0LnUF05L231J0M")
    @POST("find")
    suspend fun getPubs(@Body pubService : PubService = PubService()): PubContainerDto
}

object PubApi{
    val retroFitService : PubRestApi by lazy {
        retrofit.create(PubRestApi::class.java)
    }
}


