package com.example.semestralnezadanie.database.pubs

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.example.semestralnezadanie.api.ApiRest
import com.example.semestralnezadanie.api.PubGeneralResponse
import com.example.semestralnezadanie.database.LocalCache
import com.example.semestralnezadanie.entities.CurrentLocation
import com.example.semestralnezadanie.entities.NearbyPub
import java.io.IOException

class PubsDataRepository private constructor(private val localCache : LocalCache, private val apiService : ApiRest)
{

    companion object{
        @Volatile
        private var INSTANCE : PubsDataRepository? = null

        fun getInstance(apiService: ApiRest, localCache: LocalCache) : PubsDataRepository =
            INSTANCE ?: synchronized(this)
            {
                INSTANCE ?: PubsDataRepository(localCache, apiService).also { INSTANCE = it }
            }

    }

    suspend fun getPubList()
    {
        try{
            val response = apiService.getPubList()

            if(response.isSuccessful)
            {
                response.body()?.let { pubResponse: List<PubGeneralResponse> ->
                    var pubs = pubResponse.map {
                        PubsModel(
                            it.pubId, it.pubName, it.pubAmenity,
                            it.latitude.toString(), it.longitude.toString(), it.users
                        )
                    }
                    localCache.deletePubs()
                    localCache.insertPubs(pubs)
                }
            }
            else
            {
                Log.e("Failed read", "Failed to read pubs")
            }
        }
        catch (e0 : Exception)
        {
            e0.printStackTrace()
            Log.e("Network error", "Check your network please")
        }
        catch (e1 : Exception)
        {
            e1.printStackTrace()
            Log.e("Loading error", "Failed to load all pubs")
        }
    }

    fun storePubs() : LiveData<List<PubsModel>?>
    {
        val cached = localCache.getPubs()

        return cached
    }

    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun getPubDetail(pubId : String) : NearbyPub
    {
        lateinit var nearbyPub : NearbyPub

        try{
            val jsonQuery = "[out:json];node($pubId);out body;>;out skel;"
            val response = apiService.detailPub(jsonQuery)

            if(response.isSuccessful)
            {
                response.body()?.let { pubResponse ->
                    if(pubResponse.elements.isNotEmpty())
                    {
                        val pubs = pubResponse.elements[0]
                        nearbyPub = NearbyPub(
                            pubs.id, pubs.tags.getOrDefault("name", ""),
                            pubs.tags.getOrDefault("amenity", ""),
                            pubs.latitude,
                            pubs.longitude,
                            pubs.tags
                        )
                    }
                }
            }
            else
            {
                Log.e("Error", "Error reading pubs")
            }
        }
        catch (e0 : IOException)
        {
            e0.printStackTrace()
            Log.e("Network error", "Please check your network error")
        }
        catch (e1 : Exception)
        {
            e1.printStackTrace()
            Log.e("Loading failed", "Failed loading pubs")
        }

        return nearbyPub
    }

    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun getPubsNearby(latitude : Double, longitude : Double) : List<NearbyPub>
    {
        var nearbyPubs = listOf<NearbyPub>()

        try{
            val jsonQuery = "[out:json];node(around:250,$latitude,$longitude);(node(around:250)[\"amenity\"~\"^pub$|^bar$|^restaurant$|^cafe$|^fast_food$|^stripclub$|^nightclub$\"];);out body;>;out skel;"
            var response = apiService.nearbyPub(jsonQuery)

            if(response.isSuccessful)
            {
                response.body()?.let { pubResponse ->

                    nearbyPubs = pubResponse.elements.map {
                        NearbyPub(it.id, it.tags.getOrDefault("name", ""), it.tags.getOrDefault("amenity", ""), it.latitude, it.longitude, it.tags).apply {
                            distance = getDistanceToPubNearby(CurrentLocation(latitude, longitude))
                        }
                    }
                    nearbyPubs = nearbyPubs.filter { it.pubName.isNotBlank() }.sortedBy { it.distance }
                }
            }
            else
            {
                Log.e("Failed loading", "Failed to load pubs")
            }
        }
        catch (e0 : IOException)
        {
            e0.printStackTrace()
            Log.e("Failed loading", "Please check your network")
        }
        catch (e1 : Exception)
        {
            e1.printStackTrace()
            Log.e("Failed loading", "unexprected error")
        }

        return nearbyPubs
    }

    //TODO: Checkin pubs
}