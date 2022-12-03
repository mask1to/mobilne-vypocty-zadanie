package com.example.semestralnezadanie.fragments.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.semestralnezadanie.database.friends.FriendsDataRepository
import com.example.semestralnezadanie.fragments.viewmodels.FriendsViewModel
import com.example.semestralnezadanie.fragments.viewmodels.LoginRegisterViewModel

class FriendViewModelFactory(private val friendsDataRepository: FriendsDataRepository) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FriendsViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return FriendsViewModel(friendsDataRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel entity class")
    }
}