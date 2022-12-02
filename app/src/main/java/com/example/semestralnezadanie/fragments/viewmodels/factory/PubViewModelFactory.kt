package com.example.semestralnezadanie.fragments.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.semestralnezadanie.database.pubs.PubsDataRepository
import com.example.semestralnezadanie.fragments.viewmodels.InfoViewModel
import com.example.semestralnezadanie.fragments.viewmodels.PubsViewModel


// https://www.youtube.com/watch?v=CAnkC29X_Ds&ab_channel=TechProjects
class PubViewModelFactory(private val pubsDataRepository: PubsDataRepository) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if(modelClass.isAssignableFrom(InfoViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return InfoViewModel(pubsDataRepository) as T
        }

        if(modelClass.isAssignableFrom(PubsViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return PubsViewModel(pubsDataRepository) as T
        }

        throw IllegalArgumentException("Uknown ViewModel entity class")
    }
}