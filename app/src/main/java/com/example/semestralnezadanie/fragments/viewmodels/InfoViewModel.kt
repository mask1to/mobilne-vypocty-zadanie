package com.example.semestralnezadanie.fragments.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.semestralnezadanie.database.pubs.PubsDataRepository
import com.example.semestralnezadanie.other.NearbyPub
import com.example.semestralnezadanie.other.PubDetail
import com.example.semestralnezadanie.fragments.viewmodels.wrappers.LiveDataEvent
import kotlinx.coroutines.launch

class InfoViewModel(private val pubsDataRepository: PubsDataRepository) : ViewModel()
{
    private val _message = MutableLiveData<LiveDataEvent<String>>()
    val message : LiveData<LiveDataEvent<String>> get() = _message

    val loadData = MutableLiveData(false)
    val pub = MutableLiveData<NearbyPub>(null)
    @RequiresApi(Build.VERSION_CODES.N)
    val amenity = pub.map {
        it?.tags?.getOrDefault("amenity", "") ?: ""
    }
    val props : LiveData<List<PubDetail>> = pub.switchMap {
        liveData {
            it?.let {
                emit(it.tags.map {
                    PubDetail(it.key, it.value)
                })
            } ?: emit(emptyList<PubDetail>())
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadPubs(pid : String)
    {
        if(pid.isNotBlank())
        {
            viewModelScope.launch {
                loadData.postValue(true)
                pub.postValue(pubsDataRepository.getPubDetail(pid){ _message.postValue(LiveDataEvent(it)) })
                loadData.postValue(false)
            }
        }
        else
        {
            return
        }
    }
}