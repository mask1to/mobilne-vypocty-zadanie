package com.example.semestralnezadanie.fragments.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.semestralnezadanie.api.ApiRest
import com.example.semestralnezadanie.database.DatabasePubs
import com.example.semestralnezadanie.database.LocalCache
import com.example.semestralnezadanie.database.pubs.PubsDataRepository
import com.example.semestralnezadanie.database.users.UserDataRepository

class ViewModelHelper
{
    fun grantLocalCache(context: Context) : LocalCache
    {
        val db = DatabasePubs.getDatabase(context)
        return LocalCache(db.pubsDao())
    }

    fun getUserRepository(context : Context) : UserDataRepository
    {
        return UserDataRepository.getInstance(ApiRest.buildAndCreate(context), )
    }

    fun getPubRepository(context : Context) : PubsDataRepository
    {
        return PubsDataRepository.getInstance(ApiRest.buildAndCreate(context), )
    }

    /*
    fun provideViewModel(context : Context) : ViewModelProvider.Factory
    {

    }*/
}