package com.example.semestralnezadanie.fragments.viewmodels

import androidx.lifecycle.*
import com.example.semestralnezadanie.api.FriendContact
import com.example.semestralnezadanie.api.FriendsGeneralResponse
import com.example.semestralnezadanie.api.UserGeneralResponse
import com.example.semestralnezadanie.database.friends.FriendsDataRepository
import com.example.semestralnezadanie.database.friends.FriendsModel
import com.example.semestralnezadanie.fragments.viewmodels.wrappers.LiveDataEvent
import kotlinx.coroutines.launch

class FriendsViewModel(private val friendsDataRepository: FriendsDataRepository) : ViewModel()
{
    private val _message = MutableLiveData<LiveDataEvent<String>>()
    val message : LiveData<LiveDataEvent<String>> get() = _message

    val loadData = MutableLiveData(false)

    var allFriends : LiveData<List<FriendsModel>?>? = null

    fun getFriendList(id : Long)
    {
        allFriends = liveData {
            loadData.postValue(true)
            friendsDataRepository.getFriendList({_message.postValue(LiveDataEvent(it))}, id)
            loadData.postValue(false)
            emitSource(friendsDataRepository.storeFriends(id))
        }
    }

    fun addFriend(contact : FriendContact)
    {
        viewModelScope.launch {
            loadData.postValue(true)
            friendsDataRepository.addFriend({_message.postValue(LiveDataEvent(it))}, contact)
            loadData.postValue(false)
        }
    }

    fun removeFriend(contact : FriendContact)
    {
        viewModelScope.launch {
            loadData.postValue(true)
            friendsDataRepository.removeFriend({_message.postValue(LiveDataEvent(it))}, contact)
            loadData.postValue(false)
        }
    }

    fun refreshAll(id : Long)
    {
        viewModelScope.launch {
            loadData.postValue(true)
            friendsDataRepository.getFriendList({_message.postValue(LiveDataEvent(it))}, id)
            loadData.postValue(false)
        }
    }

    fun showMessage(message : String)
    {
        _message.postValue(LiveDataEvent(message))
    }
}