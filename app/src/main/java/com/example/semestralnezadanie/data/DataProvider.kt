package com.example.semestralnezadanie.data

import android.content.Context
import com.example.semestralnezadanie.R
import com.example.semestralnezadanie.entities.Pub
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class DataProvider(private val context : Context)
{

    private fun fetchJsonFromRaw(context: Context): String? {
        val jsonString: String
        try {
            jsonString = context.resources.openRawResource(R.raw.pubs).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    private fun extractValue(jsonObj : JSONObject, nameForTag : String) : String{
        return try
        {
            jsonObj.getString(nameForTag)
        }
        catch (exception : JSONException)
        {
            "Found exception"
        }
    }
}