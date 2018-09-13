package com.matheusfroes.swapi.data

import android.arch.lifecycle.LiveData
import com.matheusfroes.swapi.data.dto.BookmarkedEvent
import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.data.source.LocalSource
import com.matheusfroes.swapi.data.source.RemoteSource
import com.matheusfroes.swapi.network.Connectivity
import javax.inject.Inject

// Local database as the single source of truth. Every data presented on the UI is from the local database
class AppRepository @Inject constructor(
        private val local: LocalSource,
        private val remote: RemoteSource,
        private val connectivity: Connectivity) {

    fun getPeople(): LiveData<List<Person>> {
        return local.getPeople()
    }

    fun getBookmarkedPeople(): LiveData<List<Person>> {
        return local.getBookmarkedPeople()
    }

    suspend fun sendPendingBookmarks() {
        val pending = local.getPendingBookmarks()

        pending.forEach { pendingBookmark ->
            val response = bookmarkPerson(pendingBookmark.personId)

            if (response.bookmarked) {
                local.bookmarkPerson(pendingBookmark.personId)
                local.removePendingBookmark(pendingBookmark.personId)
            }
        }
    }

    suspend fun fetchPeople(page: Int) {
        val people = remote.getPeople(page)
        local.savePeople(people)
    }

    suspend fun getPerson(personId: Long): Person {
        return local.getPerson(personId)
    }

    suspend fun bookmarkPerson(personId: Long): BookmarkedEvent {
        local.bookmarkPerson(personId)

        if (connectivity.isConnected()) {
            val response = remote.bookmarkPerson(personId)
            if (!response.bookmarked) {
                local.unbookmarkPerson(personId)
                local.addPendingBookmark(personId)
            } else {
                local.removePendingBookmark(personId)
            }

            return response
        } else {
            local.addPendingBookmark(personId)
            return BookmarkedEvent(false, "Bookmark will be sent when connection available")
        }
    }

    suspend fun searchPeople(page: Int, query: String): List<Person> {
        val people = remote.searchPeople(page, query)
        local.savePeople(people)
        return people
    }

    suspend fun unbookmarkPerson(personId: Long) {
        local.unbookmarkPerson(personId)
    }

    suspend fun getSpecies(speciesUrl: List<String>): List<String> {
        return remote.getSpecies(speciesUrl)
    }

    suspend fun getHomeworld(planetUrl: String): String {
        return remote.getPlanet(planetUrl)
    }
}