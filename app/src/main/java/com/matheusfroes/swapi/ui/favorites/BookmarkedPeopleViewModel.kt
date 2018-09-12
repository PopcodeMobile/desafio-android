package com.matheusfroes.swapi.ui.favorites

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.matheusfroes.swapi.data.AppRepository
import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.extra.uiContext
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class BookmarkedPeopleViewModel @Inject constructor(
        private val repository: AppRepository) : ViewModel() {

    fun getBookmarkedPeople(): LiveData<List<Person>> {
        return repository.getBookmarkedPeople()
    }

    fun unbookmarkPerson(personId: Long) = launch(uiContext) {
        repository.unbookmarkPerson(personId)
    }
}