package com.example.semestralnezadanie.api.authenticator

import android.content.Context
import kotlinx.coroutines.internal.synchronized
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/*
class TokenAuthenticator(val context: Context) : Authenticator
{
    override fun authenticate(route: Route?, response: Response): Request?
    {
        synchronized(this){
            if(response.request().header("mobv-auth")?.compareTo("accept") == 0 && response.code() == 401)
            {
                // handle shared preferences
            }
            else if(response.request().header("mobv-auth")?.compareTo("accept") == 0 && response.code() == 400)
            {
                // ??
            }
            else if(response.request().header("mobv-auth")?.compareTo("accept") == 0 && response.code() == 403)
            {
                // show forbidden message
            }
            else if(response.request().header("mobv-auth")?.compareTo("accept") == 0 && response.code() == 401)
            {
                // show notFound message
            }
        }
    }

}*/