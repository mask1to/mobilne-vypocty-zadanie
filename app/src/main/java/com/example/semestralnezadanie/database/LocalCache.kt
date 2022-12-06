package com.example.semestralnezadanie.database

import androidx.lifecycle.LiveData
import com.example.semestralnezadanie.database.friends.FriendsModel
import com.example.semestralnezadanie.database.pubs.PubsModel

class LocalCache(val pubsDbDao: DatabasePubsDao)
{
    suspend fun insertPubs(pubs : List<PubsModel>)
    {
        pubsDbDao.insertPubs(pubs)
    }

    suspend fun insertFriends(friends : List<FriendsModel>)
    {
        pubsDbDao.insertFriends(friends)
    }

    suspend fun deletePubs()
    {
        pubsDbDao.deletePubs()
    }

    suspend fun deleteFriends()
    {
        pubsDbDao.deleteFriends()
    }

    fun getPubs() : LiveData<List<PubsModel>?> = pubsDbDao.getPubs()
    fun sortPubs() : LiveData<List<PubsModel>?> = pubsDbDao.sortPubs()
    fun getFriends(id : Long) : LiveData<List<FriendsModel>?> = pubsDbDao.getFriends(id)

    fun getPub(id : Long) : PubsModel = pubsDbDao.getPub(id)
}