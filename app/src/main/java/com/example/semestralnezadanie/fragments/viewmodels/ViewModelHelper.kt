package com.example.semestralnezadanie.fragments.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.semestralnezadanie.api.ApiRest
import com.example.semestralnezadanie.database.DatabasePubs
import com.example.semestralnezadanie.database.LocalCache
import com.example.semestralnezadanie.database.friends.FriendsDataRepository
import com.example.semestralnezadanie.database.pubs.PubsDataRepository
import com.example.semestralnezadanie.database.users.UserDataRepository
import com.example.semestralnezadanie.fragments.viewmodels.factory.FriendViewModelFactory
import com.example.semestralnezadanie.fragments.viewmodels.factory.PubViewModelFactory
import com.example.semestralnezadanie.fragments.viewmodels.factory.UserViewModelFactory

object ViewModelHelper
{
    private fun grantLocalCache(context: Context): LocalCache
    {
        val db = DatabasePubs.getDatabase(context)
        return LocalCache(db.pubsDao())
    }

    private fun getUserRepository(context: Context): UserDataRepository
    {
        return UserDataRepository.getInstance(ApiRest.buildAndCreate(context))
    }

    private fun getPubRepository(context: Context): PubsDataRepository
    {
        return PubsDataRepository.getInstance(
            ApiRest.buildAndCreate(context),
            grantLocalCache(context)
        )
    }

    private fun getFriendRepository(context : Context) : FriendsDataRepository
    {
        return FriendsDataRepository.getInstance(
            ApiRest.buildAndCreate(context), grantLocalCache(context)
        )
    }

    fun provideUserViewModelFactory(context : Context) : ViewModelProvider.Factory
    {
        return UserViewModelFactory(getUserRepository(context))
    }

    fun providePubViewModelFactory(context : Context) : ViewModelProvider.Factory
    {
        return PubViewModelFactory(getPubRepository(context))
    }

    fun provideFriendViewModelFactory(context : Context) : ViewModelProvider.Factory
    {
        return FriendViewModelFactory(getFriendRepository(context))
    }
}