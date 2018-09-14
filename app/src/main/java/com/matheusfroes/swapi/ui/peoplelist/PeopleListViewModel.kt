package com.matheusfroes.swapi.ui.peoplelist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.matheusfroes.swapi.data.AppRepository
import com.matheusfroes.swapi.data.dto.BookmarkedEvent
import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.extra.Result
import com.matheusfroes.swapi.extra.SingleLiveEvent
import com.matheusfroes.swapi.extra.uiContext
import com.matheusfroes.swapi.network.Connectivity
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class PeopleListViewModel @Inject constructor(
        private val repository: AppRepository,
        private val connectivity: Connectivity
) : ViewModel() {
    val bookmarkEvent = SingleLiveEvent<BookmarkedEvent>()
    val dataFetchEvent = SingleLiveEvent<Result<Any>>()

    val peopleObservable: LiveData<List<Person>> = repository.getPeople()

    init {
        launch { repository.sendPendingBookmarks() }
    }

    fun fetchPeople(page: Int = 1) = launch(uiContext) {
        if (connectivity.isConnected()) {
            dataFetchEvent.value = Result.InProgress()
            try {
                repository.fetchPeople(page)
                dataFetchEvent.value = Result.Complete(Any())
            } catch (e: Exception) {
                dataFetchEvent.value = Result.Error(e)
            }
        }
    }

    fun toggleBookmark(personId: Long) = launch(uiContext) {
        val person = repository.getPerson(personId)

        if (!person.isBookmarked) {
            val bookmarkResponse = repository.bookmarkPerson(personId)
            bookmarkEvent.value = bookmarkResponse
        } else {
            repository.unbookmarkPerson(personId)
        }
    }
}