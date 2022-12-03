package com.example.semestralnezadanie.fragments.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.semestralnezadanie.api.UserGeneralResponse
import com.example.semestralnezadanie.database.pubs.PubsDataRepository
import com.example.semestralnezadanie.fragments.viewmodels.wrappers.LiveDataEvent

class LocationViewModel(private val pubsDataRepository: PubsDataRepository) : ViewModel()
{
    private val _message = MutableLiveData<LiveDataEvent<String>>()
    val message : LiveData<LiveDataEvent<String>> get() = _message

    val loadData = MutableLiveData(false)
}