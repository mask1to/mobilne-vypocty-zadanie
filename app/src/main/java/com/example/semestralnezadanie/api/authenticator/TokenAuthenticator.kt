package com.example.semestralnezadanie.api.authenticator

import android.content.Context
import com.example.semestralnezadanie.api.ApiRest
import com.example.semestralnezadanie.api.UserRefresh
import com.example.semestralnezadanie.database.preferences.Preferences
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route


class TokenAuthenticator(val context: Context) : Authenticator
{
    @OptIn(InternalCoroutinesApi::class)
    override fun authenticate(route: Route?, response: Response): Request?
    {
        synchronized(this)
        {
            if(response.request().header("mobv-auth")?.compareTo("accept") == 0 && response.code() == 401)
            {
                val getUserITem = Preferences.getInstance().getUserItem(context)

                if(getUserITem == null)
                {
                    Preferences.getInstance().clearAllData(context)
                    return null
                }

                val tokenResponse = ApiRest.buildAndCreate(context).refreshUser(UserRefresh(getUserITem.refreshToken)).execute()

                if(tokenResponse.isSuccessful)
                {
                    tokenResponse.body()?.let {
                        Preferences.getInstance().applyUserItem(context, it)
                        return response.request().newBuilder().header("authorization", "Bearer ${it.accessToken}").build()
                    }
                }

                Preferences.getInstance().clearAllData(context)
                return null
            }
            else if(response.request().header("mobv-auth")?.compareTo("") == 0 && response.code() == 400)
            {
                // ??
            }
            else if(response.request().header("mobv-auth")?.compareTo("") == 0 && response.code() == 403)
            {
                // show forbidden message
            }
            else if(response.request().header("mobv-auth")?.compareTo("") == 0 && response.code() == 401)
            {
                // show notFound message
            }
        }
        return null
    }

}