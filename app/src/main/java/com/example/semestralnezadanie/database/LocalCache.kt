package com.example.semestralnezadanie.database

import androidx.lifecycle.LiveData
import com.example.semestralnezadanie.database.pubs.PubsModel

class LocalCache(val pubsDbDao: DatabasePubsDao)
{
    suspend fun insertPubs(pubs : List<PubsModel>)
    {
        pubsDbDao.insertPubs(pubs)
    }

    suspend fun deletePubs()
    {
        pubsDbDao.deletePubs()
    }

    fun getPubs() : LiveData<List<PubsModel>?> = pubsDbDao.getPubs()
}