package com.example.semestralnezadanie.data

import android.content.Context
import com.example.semestralnezadanie.R
import com.example.semestralnezadanie.entities.Pub
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class DataProvider(private val context : Context)
{
    val allPubs : List<Pub> = loadAllData().sortedBy{it.name.uppercase()}

    private fun loadAllData() : List<Pub>
    {
        val allPubs = mutableListOf<Pub>()

        val jsonObj = JSONObject(fetchJsonFromRaw(context)!!)
        val elementsGetter = jsonObj.getJSONArray("elements")

        for (index in 0 until elementsGetter.length())
        {
            val pub = elementsGetter.getJSONObject(index)
            val tags = pub.getJSONObject("tags")

            allPubs.add(
                Pub(
                    name = extractValue(tags, "name"),
                    amenity = extractValue(tags, "amenity"),
                    latitude = pub.getDouble("lat").toFloat(),
                    longitude = pub.getDouble("lon").toFloat(),
                    opening_hours = extractValue(tags, "opening_hours"),
                    website = extractValue(tags, "website"),
                    contactPhone = extractValue(tags, "phone")
                )
            )
        }
        return allPubs
    }

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