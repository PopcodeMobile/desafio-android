package com.matheusfroes.swapi.ui.persondetail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.matheusfroes.swapi.Result
import com.matheusfroes.swapi.SingleLiveEvent
import com.matheusfroes.swapi.data.AppRepository
import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.uiContext
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

typealias BookmarkedMessage = String

class PersonDetailViewModel @Inject constructor(
        private val repository: AppRepository
) : ViewModel() {
    private val _personDetailObservable = MutableLiveData<Result<Person>>()
    val personDetailObservable: LiveData<Result<Person>> = _personDetailObservable

    val bookmarkedPersonEvent = SingleLiveEvent<BookmarkedMessage>()

    lateinit var person: Person
    var personId: Long = 0
        set(value) {
            field = value
            getPerson(personId)
        }

    private fun getPerson(personId: Long) = launch(uiContext) {
        _personDetailObservable.value = Result.InProgress()

        try {
            person = repository.getPerson(personId)

            _personDetailObservable.value = Result.Complete(person)
        } catch (e: Exception) {
            _personDetailObservable.value = Result.Error(e)
        }
    }

    fun toggleBookmarkPerson() = launch(uiContext) {
        if (person.isBookmarked) {
            repository.unbookmarkPerson(personId)
        } else {
            repository.bookmarkPerson(personId)
        }
    }
}