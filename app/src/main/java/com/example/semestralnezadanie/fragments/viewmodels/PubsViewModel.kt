package com.example.semestralnezadanie.fragments.viewmodels

import androidx.lifecycle.*
import com.example.semestralnezadanie.database.pubs.PubsDataRepository
import com.example.semestralnezadanie.database.pubs.PubsModel
import com.example.semestralnezadanie.fragments.viewmodels.wrappers.LiveDataEvent
import kotlinx.coroutines.launch

class PubsViewModel(private val pubsDataRepository: PubsDataRepository) : ViewModel()
{
    private val _message = MutableLiveData<LiveDataEvent<String>>()
    val message : LiveData<LiveDataEvent<String>> get() = _message

    val loadData = MutableLiveData(false)

    val allPubs : LiveData<List<PubsModel>?> = liveData {
        loadData.postValue(true)
        pubsDataRepository.getPubList()

        loadData.postValue(false)
        emitSource(pubsDataRepository.storePubs())
    }

    fun showMessage(message : String)
    {
        _message.postValue(LiveDataEvent(message))
    }

    fun refreshAll()
    {
        viewModelScope.launch{
            loadData.postValue(true)
            pubsDataRepository.getPubList()
            loadData.postValue(false)
        }
    }

}