package com.matheusfroes.swapi.ui.peoplelist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.matheusfroes.swapi.data.AppRepository
import com.matheusfroes.swapi.data.dto.BookmarkedEvent
import com.matheusfroes.swapi.data.model.Person
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

    init {
        launch { repository.sendPendingBookmarks() }
    }

    fun getPeople(): LiveData<List<Person>> {
        return repository.getPeople()
    }

    fun fetchPeople(page: Int = 1) = launch(uiContext) {
        if (connectivity.isConnected()) {
            repository.fetchPeople(page)
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