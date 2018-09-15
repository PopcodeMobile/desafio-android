package com.matheusfroes.swapi.ui.peoplelist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.matheusfroes.swapi.data.AppRepository
import com.matheusfroes.swapi.data.dto.BookmarkedEvent
import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.extra.Result
import com.matheusfroes.swapi.extra.SingleLiveEvent
import com.matheusfroes.swapi.extra.uiContext
import com.matheusfroes.swapi.ui.Listing
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class PeopleListViewModel @Inject constructor(
        private val repository: AppRepository) : ViewModel() {
    val bookmarkEvent = SingleLiveEvent<BookmarkedEvent>()

    private val initState = SingleLiveEvent<Any>()

    private val peopleResult: LiveData<Listing<Person>> = Transformations.map(initState) { repository.getPeople() }
    val people: LiveData<PagedList<Person>> = Transformations.switchMap(peopleResult) { it.pagedList }
    val networkState: LiveData<Result<Any>> = Transformations.switchMap(peopleResult) { it.networkState }

    init {
        initState.call()
        launch { repository.sendPendingBookmarks() }
    }

    fun toggleBookmark(personId: Int) = launch(uiContext) {
        val person = repository.getPerson(personId)

        if (!person.isBookmarked) {
            val bookmarkResponse = repository.bookmarkPerson(personId)
            bookmarkEvent.value = bookmarkResponse
        } else {
            repository.unbookmarkPerson(personId)
        }
    }
}