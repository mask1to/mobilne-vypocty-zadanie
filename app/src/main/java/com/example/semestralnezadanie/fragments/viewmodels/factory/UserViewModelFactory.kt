package com.example.semestralnezadanie.fragments.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.semestralnezadanie.database.users.UserDataRepository
import com.example.semestralnezadanie.fragments.viewmodels.LoginRegisterViewModel

// https://www.youtube.com/watch?v=CAnkC29X_Ds&ab_channel=TechProjects
class UserViewModelFactory(private val userDataRepository: UserDataRepository) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if(modelClass.isAssignableFrom(LoginRegisterViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return LoginRegisterViewModel(userDataRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel entity class")
    }
}