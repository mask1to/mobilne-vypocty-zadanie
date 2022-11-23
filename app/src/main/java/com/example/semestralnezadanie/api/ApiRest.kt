package com.example.semestralnezadanie.api

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/*
interface ApiRest
{
    companion object{
        const val BASE_URL = "https://zadanie.mpage.sk"

        fun buildAndCreate(context: Context) : ApiRest{
            val client = OkHttpClient.Builder()
                .addInterceptor()
                .authenticator()
                .build()

            val retrofitBuilder = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofitBuilder.create(ApiRest::class.java)
        }
    }

    @GET("https://overpass-api.de/api/interpreter?")
    suspend fun nearbyPub(
        @Query("data") data : String
    ) : Response<PubDetailResponse>

    @GET("https://overpass-api.de/api/interpreter?")
    suspend fun detailPub(
        @Query("data") data : String
    ) : Response<PubDetailResponse>

    @GET("bar/list.php")
    @Headers("mobv-auth: accept")
    suspend fun getPubList() : Response<List<PubGeneralResponse>>

    @POST("bar/message.php")
    @Headers("mobv-auth: accept")
    suspend fun postPubMessage(
        @Body pub : PubMessageRequest
    ) : Response<Any>

    @POST("user/create.php")
    suspend fun createUser(
        @Body user : UserRequest
    ) : Response<UserGeneralResponse>

    @POST("user/login.php")
    suspend fun userLogin(
        @Body user : UserLoginRequest
    ) : Response<UserGeneralResponse>
}
*/
