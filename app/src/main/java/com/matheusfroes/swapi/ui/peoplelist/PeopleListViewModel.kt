package com.matheusfroes.swapi.ui.peoplelist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.matheusfroes.swapi.Connectivity
import com.matheusfroes.swapi.Result
import com.matheusfroes.swapi.data.AppRepository
import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.uiContext
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class PeopleListViewModel @Inject constructor(
        private val repository: AppRepository,
        private val connectivity: Connectivity
) : ViewModel() {
    private val _peopleObservable = MediatorLiveData<Result<List<Person>>>()
    val peopleObservable: LiveData<Result<List<Person>>> = _peopleObservable

    private var apiPage: Int = 1

    fun getPeople(page: Int = 1) = launch(uiContext) {
        apiPage = page
        _peopleObservable.value = Result.InProgress()

        try {
            if (connectivity.isConnected()) {
                repository.fetchPeople(page)
            }

            val people = repository.getPeople()
            _peopleObservable.value = Result.Complete(people)
        } catch (e: Exception) {
            _peopleObservable.value = Result.Error(e)
        }
    }


}