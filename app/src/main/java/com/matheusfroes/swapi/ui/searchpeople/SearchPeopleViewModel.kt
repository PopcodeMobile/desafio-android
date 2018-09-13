package com.matheusfroes.swapi.ui.searchpeople

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.matheusfroes.swapi.data.AppRepository
import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.extra.uiContext
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class SearchPeopleViewModel @Inject constructor(
        private val repository: AppRepository
) : ViewModel() {
    private val _searchResult = MutableLiveData<List<Person>>()
    val searchResult: LiveData<List<Person>> = _searchResult

    var searchQuery: String = ""

    fun searchPeople(page: Int = 1) = launch(uiContext) {
        val result = repository.searchPeople(page, searchQuery)
        _searchResult.value = result
    }

}