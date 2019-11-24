package com.example.starwarswiki.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import timber.log.Timber
import java.lang.IllegalArgumentException

class PersonListViewModelFactory(val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PersonListViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            Timber.d("Instance of PersonViewModel created !")
            return PersonListViewModel(application) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}