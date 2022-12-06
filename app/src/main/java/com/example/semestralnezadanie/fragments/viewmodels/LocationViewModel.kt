package com.example.semestralnezadanie.fragments.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.semestralnezadanie.database.pubs.PubsDataRepository
import com.example.semestralnezadanie.other.NearbyPub
import com.example.semestralnezadanie.other.UserCurrentLocation
import com.example.semestralnezadanie.fragments.viewmodels.wrappers.LiveDataEvent
import kotlinx.coroutines.launch

class LocationViewModel(private val pubsDataRepository: PubsDataRepository) : ViewModel()
{
    private val _message = MutableLiveData<LiveDataEvent<String>>()
    val message : LiveData<LiveDataEvent<String>> get() = _message

    val userLocation = MutableLiveData<UserCurrentLocation>(null)
    val userPub = MutableLiveData<NearbyPub>(null)

    val loadData = MutableLiveData(false)

    private val _isCheckedIn = MutableLiveData<LiveDataEvent<Boolean>>()
    val checkedIn : LiveData<LiveDataEvent<Boolean>>
        get() = _isCheckedIn


    @RequiresApi(Build.VERSION_CODES.N)
    val nearbyPubs : LiveData<List<NearbyPub>> = userLocation.switchMap {
        liveData {
            loadData.postValue(true)
            it?.let { it ->
                val getPubs = pubsDataRepository.getPubsNearby(it.userLatitude, it.userLongitude) {
                    _message.postValue(
                        LiveDataEvent(it)
                    )
                }
                emit(getPubs)
                if(userPub.value == null)
                {
                    userPub.postValue(getPubs.firstOrNull())
                }
            } ?: emit(listOf())
            loadData.postValue(false)
        }
    }

    fun showMessage(message : String)
    {
        _message.postValue(LiveDataEvent(message))
    }

    fun checkInUser()
    {
        viewModelScope.launch {
            loadData.postValue(true)
            userPub.value?.let {
                pubsDataRepository.pubCheckIn(it, {_message.postValue(LiveDataEvent(it))}, {_isCheckedIn.postValue(LiveDataEvent(it))})
            }
            loadData.postValue(false)
        }
    }
}