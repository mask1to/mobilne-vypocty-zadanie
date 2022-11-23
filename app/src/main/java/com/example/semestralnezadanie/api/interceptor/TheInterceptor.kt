package com.example.semestralnezadanie.api.interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

/*
class TheInterceptor(val context: Context) : Interceptor
{
    override fun intercept(chain: Interceptor.Chain): Response {
        synchronized(this)
        {
            val request = chain.request().newBuilder()
                .addHeader("User-Agent", "Mobv-Android/1.0.0")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")

            if(chain.request().header("mobv-auth")?.compareTo("accept") == 0)
            {
                request.addHeader(
                    "Authorization"
                )
            }
            
            //TODO: SharedPreferences

            val response = chain.proceed(request.build())

            return response
        }
    }

}*/