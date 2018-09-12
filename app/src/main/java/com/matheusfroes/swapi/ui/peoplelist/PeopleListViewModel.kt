package com.matheusfroes.swapi.ui.peoplelist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.matheusfroes.swapi.Connectivity
import com.matheusfroes.swapi.SingleLiveEvent
import com.matheusfroes.swapi.data.AppRepository
import com.matheusfroes.swapi.data.dto.BookmarkedEvent
import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.uiContext
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class PeopleListViewModel @Inject constructor(
        private val repository: AppRepository,
        private val connectivity: Connectivity
) : ViewModel() {
    private var apiPage: Int = 1
    val bookmarkEvent = SingleLiveEvent<BookmarkedEvent>()
    val searchQuery: String = ""

    init {
        launch { repository.sendPendingBookmarks() }
    }

    fun getPeople(): LiveData<List<Person>> {
        return repository.getPeople()
    }

    fun fetchPeople(page: Int = 1) = launch(uiContext) {
        apiPage = page
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