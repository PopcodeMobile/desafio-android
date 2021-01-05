package com.example.starwars.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.starwars.repository.RepositoryApi

class PeopleViewModelFactory(private val repository: RepositoryApi) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PeopleViewModel(repository) as T
    }
}
