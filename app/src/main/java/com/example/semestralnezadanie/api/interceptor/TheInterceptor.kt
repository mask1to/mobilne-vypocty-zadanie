package com.example.semestralnezadanie.api.interceptor

import android.content.Context
import com.example.semestralnezadanie.database.preferences.Preferences
import okhttp3.Interceptor
import okhttp3.Response

//src: https://blog.codavel.com/how-to-create-an-http-interceptor-for-an-android-app-using-okhttp3
class TheInterceptor(val context: Context) : Interceptor
{
    override fun intercept(chain: Interceptor.Chain): Response {
        synchronized(this)
        {
            val request = chain.request().newBuilder()
                .addHeader("User-Agent", "Mobv-Android/1.0.0")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")

            if (chain.request().header("mobv-auth")?.compareTo("accept") == 0) {
                request.addHeader(
                    "Authorization",
                    "Bearer ${Preferences.getInstance().getUserItem(context)?.accessToken}"
                )
            }

            Preferences.getInstance().getUserItem(context)?.userId.let {
                request.addHeader("x-user", it)
            }
            request.addHeader("x-apikey", "c95332ee022df8c953ce470261efc695ecf3e784")

            return chain.proceed(request.build())
        }
    }

}