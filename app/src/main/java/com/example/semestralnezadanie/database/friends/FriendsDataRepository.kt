package com.example.semestralnezadanie.database.friends

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.semestralnezadanie.api.ApiRest
import com.example.semestralnezadanie.api.FriendContact
import com.example.semestralnezadanie.api.FriendsGeneralResponse
import com.example.semestralnezadanie.api.UserGeneralResponse
import com.example.semestralnezadanie.database.LocalCache
import com.example.semestralnezadanie.database.preferences.Preferences

class FriendsDataRepository private constructor(private val localCache: LocalCache, private val apiService: ApiRest)
{
    companion object{
        @Volatile
        private var INSTANCE : FriendsDataRepository? = null

        fun getInstance(apiService: ApiRest, localCache: LocalCache) : FriendsDataRepository =
            INSTANCE ?: synchronized(this)
            {
                INSTANCE ?: FriendsDataRepository(localCache, apiService).also { INSTANCE = it }
            }

    }

    suspend fun addFriend(errorOut: (error : String) -> Unit, contact : FriendContact)
    {
        try{
            val response = apiService.addFriend(contact)

            if(response.isSuccessful)
            {
                errorOut("Úspešné pridanie")
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
                errorOut("Pridanie priateľa zlyhalo")
            }
        }
        catch (e0 : Exception)
        {
            e0.printStackTrace()
            errorOut("Pridanie priateľa zlyhalo, skontrolujte si internetové pripojenie")
        }
        catch (e1 : Exception)
        {
            e1.printStackTrace()
            errorOut("Pridanie priateľa zlyhalo")
        }
    }

    suspend fun removeFriend(errorOut: (error : String) -> Unit, contact : FriendContact)
    {
        try{
            val response = apiService.removeFriend(contact)

            if(response.isSuccessful)
            {
                errorOut("Úspešné vymazanie")
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
                errorOut("Vymazanie priateľa zlyhalo")
            }
        }
        catch (e0 : Exception)
        {
            e0.printStackTrace()
            errorOut("Vymazanie priateľa zlyhalo, skontrolujte si internetové pripojenie")
        }
        catch (e1 : Exception)
        {
            e1.printStackTrace()
            errorOut("Vymazanie priateľa zlyhalo")
        }
    }

    suspend fun getFriendList(errorOut: (error : String) -> Unit, user_id: Long)
    {
        try{
            val response = apiService.getFriendList()

            if(response.isSuccessful)
            {
                response.body()?.let { friendResponse: List<FriendsGeneralResponse> ->
                    var friends = friendResponse.map {
                        FriendsModel(
                            null, user_id, it.userId, it.userName,
                            it.pubId, it.pubName, it.time, it.pubLatitude, it.pubLongitude
                        )
                    }
                    localCache.deleteFriends()
                    localCache.insertFriends(friends)
                } ?: errorOut("Načítanie priateľov zlyhalo")
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
                errorOut("Načítanie priateľov zlyhalo")
            }
        }
        catch (e0 : Exception)
        {
            e0.printStackTrace()
            errorOut("Načítanie priateľov zlyhalo, skontrolujte si internetové pripojenie")
        }
        catch (e1 : Exception)
        {
            e1.printStackTrace()
            errorOut("Načítanie všetkých priateľov zlyhalo")
        }
    }

    fun storeFriends(id : Long) : LiveData<List<FriendsModel>?>
    {
        val cached = localCache.getFriends(id)
        return cached
    }

}