package com.example.semestralnezadanie.database.pubs

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.example.semestralnezadanie.api.ApiRest
import com.example.semestralnezadanie.api.PubMessageRequest
import com.example.semestralnezadanie.database.LocalCache
import com.example.semestralnezadanie.other.UserCurrentLocation
import com.example.semestralnezadanie.other.NearbyPub
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
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

    suspend fun getPubList(errorOut: (error : String) -> Unit, latitude : Double, longitude : Double) : List<PubsModel>
    {
        var publist = listOf<PubsModel>()
        try{
            val response = apiService.getPubList()

            if(response.isSuccessful)
            {
                response.body()?.let { pubResponse ->
                    publist = pubResponse.map {
                        PubsModel(it.pubId.toLong(), it.pubName, it.pubAmenity, it.latitude, it.longitude, it.users).apply {
                            distance = (distanceTo(UserCurrentLocation(latitude, longitude))) / 1000.0
                        }
                    }
                    localCache.deletePubs()
                    localCache.insertPubs(publist)
                } ?: errorOut("Načítanie podnikov zlyhalo")
            }
            else if(response.code() == 400)
            {
                errorOut("Nesprávny request")
            }
            else if(response.code() == 401)
            {
                errorOut("Neautorizovaný request")
            }
            else if(response.code() == 404)
            {
                errorOut("Endpoint neexistuje")
            }
            else if(response.code() == 500)
            {
                errorOut("Chyba v databáze")
            }
            else
            {
                errorOut("Načítanie podnikov zlyhalo")
            }
        }
        catch (e0 : Exception)
        {
            e0.printStackTrace()
            errorOut("Načítanie podnikov zlyhalo, skontrolujte si internetové pripojenie")
        }
        catch (e1 : Exception)
        {
            e1.printStackTrace()
            errorOut("Načítanie všetkých podnikov zlyhalo")
        }
        Log.d("publist", publist.toList().toString())

        return publist
    }

    fun storePubs() : LiveData<List<PubsModel>?>
    {
        val cached = localCache.getPubs()
        return cached
    }

    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun getPubDetail(pubId : String, errorOut: (error : String) -> Unit) : NearbyPub
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
                        withContext(Dispatchers.IO)
                        {
                            val databasePub = localCache.getPub(pubResponse.elements[0].id.toLong())
                            Log.d("users", pubResponse.elements[0].id.toString()
                            )
                            nearbyPub.users= databasePub.users
                        }


                    }
                } ?: errorOut("Načítavanie podnikov zlyhalo")
            }
            else if(response.code() == 400)
            {
                errorOut("Nesprávny request")
            }
            else if(response.code() == 401)
            {
                errorOut("Neautorizovaný request")
            }
            else if(response.code() == 404)
            {
                errorOut("Endpoint neexistuje")
            }
            else if(response.code() == 500)
            {
                errorOut("Chyba v databáze")
            }
            else
            {
                errorOut("Načítanie podnikov zlyhalo")
            }
        }
        catch (e0 : IOException)
        {
            e0.printStackTrace()
            errorOut("Načítanie podnikov zlyhalo, skontrolujte si internetové pripojenie")
        }
        catch (e1 : Exception)
        {
            e1.printStackTrace()
            errorOut("Načítanie všetkých podnikov zlyhalo")
        }

        return nearbyPub
    }

    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun getPubsNearby(lat: Double, lon: Double, errorOut: (error: String) -> Unit) : List<NearbyPub>
    {
        var nearby = listOf<NearbyPub>()
        try {
            val q = "[out:json];node(around:250,$lat,$lon);(node(around:250)[\"amenity\"~\"^pub$|^bar$|^restaurant$|^cafe$|^fast_food$|^stripclub$|^nightclub$\"];);out body;>;out skel;"
            val response = apiService.nearbyPub(q)
            if (response.isSuccessful)
            {
                response.body()?.let { bars ->
                    nearby = bars.elements.map {
                        NearbyPub(it.id,it.tags.getOrDefault("name",""), it.tags.getOrDefault("amenity",""),it.latitude,it.longitude,it.tags).apply {
                            distance = (distanceTo(UserCurrentLocation(lat,lon)))
                        }
                    }
                    nearby = nearby.filter { it.pubName.isNotBlank() }.sortedBy { it.distance }
                } ?: errorOut("Načítanie podnikov zlyhalo")
            }
            else if(response.code() == 400)
            {
                errorOut("Nesprávny request")
            }
            else if(response.code() == 401)
            {
                errorOut("Neautorizovaný request")
            }
            else if(response.code() == 404)
            {
                errorOut("Endpoint neexistuje")
            }
            else if(response.code() == 500)
            {
                errorOut("Chyba v databáze")
            }
            else
            {
                errorOut("Načítanie podnikov zlyhalo")
            }
        }
        catch (e0 : IOException)
        {
            e0.printStackTrace()
            errorOut("Načítanie podnikov zlyhalo, skontrolujte si internetové pripojenie")
        }
        catch (e1 : Exception)
        {
            e1.printStackTrace()
            errorOut("Načítanie všetkých podnikov zlyhalo")
        }
        return nearby
    }

    suspend fun pubCheckIn(pub : NearbyPub, errorOut: (error: String) -> Unit, status : (isSuccess : Boolean) -> Unit)
    {
        try {
            val response = apiService.postPubMessage(
                PubMessageRequest(
                    pub.pubId,
                    pub.pubName,
                    pub.pubAmenity,
                    pub.lat,
                    pub.lon))
            if(response.isSuccessful)
            {
                Log.d("response", response.toString())
                response.body()?.let { _ -> status(true)
                }
            }
            else if(response.code() == 400)
            {
                errorOut("Nesprávny request")
            }
            else if(response.code() == 401)
            {
                errorOut("Neautorizovaný request")
            }
            else if(response.code() == 404)
            {
                errorOut("Endpoint neexistuje")
            }
            else if(response.code() == 500)
            {
                errorOut("Chyba v databáze")
            }
            else
            {
                errorOut("Prihlásenie zlyhalo, vyskúšajte neskôr prosím")
            }
        }
        catch (e0 : IOException)
        {
            e0.printStackTrace()
            errorOut("Prihlásenie zlyhalo, skontrolujte si internetové pripojenie")

        }
        catch (e1 : Exception)
        {
            e1.printStackTrace()
            errorOut("Prihlásenie zlyhalo, neočakávaná chyba")
        }
    }

}