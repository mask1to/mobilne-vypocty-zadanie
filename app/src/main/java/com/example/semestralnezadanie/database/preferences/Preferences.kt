package com.example.semestralnezadanie.database.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.semestralnezadanie.api.UserGeneralResponse
import com.google.gson.Gson

class Preferences private constructor()
{
    private fun getSharedPreferences(context: Context?) : SharedPreferences?{
        return context?.getSharedPreferences(
            shpKey, Context.MODE_PRIVATE
        )
    }

    fun clearAllData(context : Context?)
    {
        val sharedPreferences = getSharedPreferences(context) ?:return
        val editPref = sharedPreferences.edit()

        editPref.clear()
        editPref.apply()
    }

    fun applyUserItem(context: Context?, userItem: UserGeneralResponse?)
    {
        val sharedPreferences = getSharedPreferences(context) ?:return
        val editPref = sharedPreferences.edit()

        userItem?.let {
            editPref.putString(userKey, Gson().toJson(it))
            editPref.apply()
            return
        }
        editPref.putString(userKey, null)
        editPref.apply()
    }

    fun getUserItem(context: Context?) : UserGeneralResponse?
    {
        val sharedPreferences = getSharedPreferences(context) ?:return null
        val jsonString = sharedPreferences.getString(userKey, null) ?:return null

        return Gson().fromJson(jsonString, UserGeneralResponse::class.java)
    }

    companion object {
        @Volatile
        private var INSTANCE: Preferences? = null

        fun getInstance(): Preferences =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: Preferences().also { INSTANCE = it }
            }

        private const val shpKey = "mobvPrefData"
        private const val userKey = "userKey"
    }
}