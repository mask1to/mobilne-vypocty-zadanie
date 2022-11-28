package com.example.semestralnezadanie.fragments.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestralnezadanie.api.UserGeneralResponse
import com.example.semestralnezadanie.database.users.UserDataRepository
import com.example.semestralnezadanie.fragments.viewmodels.wrappers.LiveDataEvent
import kotlinx.coroutines.launch

class LoginRegisterViewModel(private val userDataRepository: UserDataRepository) : ViewModel()
{
    private val _message = MutableLiveData<LiveDataEvent<String>>()
    val message : LiveData<LiveDataEvent<String>> get() = _message

    val userResponse = MutableLiveData<UserGeneralResponse>(null)
    val loadData = MutableLiveData(false)

    fun showMessage(message : String)
    {
        _message.postValue(LiveDataEvent(message))
    }

    fun singIn(userName : String, userPassword : String)
    {
        viewModelScope.launch {
            loadData.postValue(true)
            userDataRepository.loginUser(userName, userPassword)
        }
        loadData.postValue(false)
    }

    fun signUp(userName: String, userPassword: String)
    {
        viewModelScope.launch {
            loadData.postValue(true)
            userDataRepository.registerUser(userName, userPassword)
        }
        loadData.postValue(false)
    }
}