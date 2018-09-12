package com.matheusfroes.swapi.ui.persondetail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.matheusfroes.swapi.data.AppRepository
import com.matheusfroes.swapi.data.dto.BookmarkedEvent
import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.extra.Result
import com.matheusfroes.swapi.extra.SingleLiveEvent
import com.matheusfroes.swapi.extra.uiContext
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class PersonDetailViewModel @Inject constructor(
        private val repository: AppRepository
) : ViewModel() {
    private val _personDetailObservable = MutableLiveData<Result<Person>>()
    val personDetailObservable: LiveData<Result<Person>> = _personDetailObservable

    private val _homeworldObservable = MutableLiveData<String>()
    val homeworldObservable: LiveData<String> = _homeworldObservable

    private val _speciesObservable = MutableLiveData<String>()
    val speciesObservable: LiveData<String> = _speciesObservable

    val bookmarkedPersonEvent = SingleLiveEvent<BookmarkedEvent>()
    val unbookmarkedPersonEvent = SingleLiveEvent<Unit>()

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

            val homeworld = async { repository.getHomeworld(person.homeworld) }
            if (person.species.isNotEmpty()) {
                val species = async { repository.getSpecies(person.species.split(",")).joinToString() }
                _speciesObservable.value = species.await()
            }
            _homeworldObservable.value = homeworld.await()
        } catch (e: Exception) {
            _personDetailObservable.value = Result.Error(e)
        }
    }

    fun toggleBookmarkPerson() = launch(uiContext) {
        if (person.isBookmarked) {
            repository.unbookmarkPerson(personId)
            person.isBookmarked = false
            unbookmarkedPersonEvent.call()
        } else {
            val bookmarkResponse = repository.bookmarkPerson(personId)
            person.isBookmarked = bookmarkResponse.bookmarked

            bookmarkedPersonEvent.value = bookmarkResponse
        }
    }
}