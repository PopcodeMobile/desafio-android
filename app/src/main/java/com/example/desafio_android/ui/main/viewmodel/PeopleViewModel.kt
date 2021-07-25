package com.example.desafio_android.ui.main.viewmodel

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.desafio_android.data.repository.Repository

class PeopleViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application,
    @Assisted state: SavedStateHandle
) : AndroidViewModel(application) {

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val EMPTY_QUERY = ""
    }

    private val currentQuery = state.getLiveData(CURRENT_QUERY, EMPTY_QUERY)
    val people = currentQuery.switchMap { query ->
        if (!query.isEmpty()) {
            repository.searchPeople(query).asLiveData()
        } else {
            repository.getPeople().cachedIn(viewModelScope).asLiveData()
        }
    }

    fun searchPeople(search: String) {
        currentQuery.value = search
    }

}