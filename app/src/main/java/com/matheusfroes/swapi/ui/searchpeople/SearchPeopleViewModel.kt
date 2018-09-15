package com.matheusfroes.swapi.ui.searchpeople

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.matheusfroes.swapi.data.AppRepository
import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.extra.Result
import javax.inject.Inject

class SearchPeopleViewModel @Inject constructor(
        private val repository: AppRepository
) : ViewModel() {
    private val query = MutableLiveData<String>()
    private val result = Transformations.map(query) { repository.searchPeople(it) }

    val search: LiveData<PagedList<Person>> = Transformations.switchMap(result) { it.pagedList }
    val networkState: LiveData<Result<Any>> = Transformations.switchMap(result) { it.networkState }

    fun searchPeople(query: String) {
        this.query.value = query
    }
}