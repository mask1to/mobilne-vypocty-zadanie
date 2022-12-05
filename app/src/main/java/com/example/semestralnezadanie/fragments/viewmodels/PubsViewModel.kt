package com.example.semestralnezadanie.fragments.viewmodels

import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.semestralnezadanie.database.pubs.PubsDataRepository
import com.example.semestralnezadanie.database.pubs.PubsModel
import com.example.semestralnezadanie.entities.NearbyPub
import com.example.semestralnezadanie.entities.UserCurrentLocation
import com.example.semestralnezadanie.fragments.viewmodels.wrappers.LiveDataEvent
import kotlinx.coroutines.launch

class PubsViewModel(private val pubsDataRepository: PubsDataRepository) : ViewModel()
{
    private val _message = MutableLiveData<LiveDataEvent<String>>()
    val message : LiveData<LiveDataEvent<String>> get() = _message

    val userLocation = MutableLiveData<UserCurrentLocation>(null)
    val loadData = MutableLiveData(false)


    @RequiresApi(Build.VERSION_CODES.N)
    val allPubs : MutableLiveData<List<PubsModel>?> = userLocation.switchMap {
        liveData {
            loadData.postValue(true)
            it?.let { it ->
                val getPubs = pubsDataRepository.getPubList({_message.postValue(LiveDataEvent(it))}, it.userLatitude, it.userLongitude)
                emit(getPubs)
            }?: emit(listOf())
            loadData.postValue(false)
            emitSource(pubsDataRepository.storePubs())
        }
    } as MutableLiveData<List<PubsModel>?>


    fun showMessage(message : String)
    {
        _message.postValue(LiveDataEvent(message))
    }

    fun refreshAll(latitude : Double, longitude : Double)
    {
        viewModelScope.launch{
            loadData.postValue(true)
            pubsDataRepository.getPubList({_message.postValue(LiveDataEvent(it))}, latitude, longitude)
            loadData.postValue(false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun sortByName()
    {
        val sorted = allPubs.value?.sortedBy { it.name }
        allPubs.value = sorted
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun sortByUsersCount()
    {
        val sorted = allPubs.value?.sortedBy { it.users }
        allPubs.value = sorted
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun sortByDistance(lat : Double, lon : Double)
    {
        val sorted = allPubs.value?.sortedBy { distanceTo(lat, lon, it.lat, it.lon) }
        allPubs.value = sorted
    }

    fun distanceTo(myLatitude : Double, myLongitude : Double, lat : Double, lon : Double): Double
    {
        return Location("").apply {
            latitude=lat
            longitude=lon
        }.distanceTo(Location("").apply {
            latitude=myLatitude
            longitude=myLongitude
        }).toDouble()
    }


}