package com.example.semestralnezadanie.api

import android.content.Context
import com.example.semestralnezadanie.api.authenticator.TokenAuthenticator
import com.example.semestralnezadanie.api.interceptor.TheInterceptor
import retrofit2.Call
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiRest
{

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

    @GET("contact/list.php")
    @Headers("mobv-auth: accept")
    suspend fun getFriendList() : Response<List<FriendsGeneralResponse>>

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

    @POST("contact/message.php")
    @Headers("mobv-auth: accept")
    suspend fun addFriend(@Body contact : FriendContact) : Response<Void>

    @POST("contact/delete.php")
    @Headers("mobv-auth: accept")
    suspend fun removeFriend(@Body contact : FriendContact) : Response<Void>

    @POST("user/refresh.php")
    fun refreshUser(@Body user : UserRefresh) : Call<UserGeneralResponse>

    companion object{
        const val BASE_URL = "https://zadanie.mpage.sk/"

        fun buildAndCreate(context: Context) : ApiRest{
            val client = OkHttpClient.Builder()
                .addInterceptor(TheInterceptor(context))
                .authenticator(TokenAuthenticator(context))
                .build()

            val retrofitBuilder = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofitBuilder.create(ApiRest::class.java)
        }
    }
}

