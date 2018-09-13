package com.matheusfroes.swapi.data.source

import android.arch.lifecycle.LiveData
import com.matheusfroes.swapi.data.AppDatabase
import com.matheusfroes.swapi.data.model.PendingBookmark
import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.extra.ioContext
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject

class LocalSource @Inject constructor(
        private val database: AppDatabase
) {

    fun getPeople(): LiveData<List<Person>> {
        return database.personDAO().getPeople()
    }

    fun getBookmarkedPeople(): LiveData<List<Person>> {
        return database.personDAO().getBookmarkedPeople()
    }

    suspend fun getPendingBookmarks(): List<PendingBookmark> = withContext(ioContext) {
        return@withContext database.personDAO().getPendingBookmarks()
    }

    suspend fun addPendingBookmark(personId: Long) = withContext(ioContext) {
        database.personDAO().addPendingBookmark(PendingBookmark(personId))
    }

    suspend fun removePendingBookmark(personId: Long) = withContext(ioContext) {
        database.personDAO().removePendingBookmark(personId)
    }

    suspend fun savePeople(people: List<Person>) = withContext(ioContext) {
        database.personDAO().insert(people)
    }

    suspend fun getPerson(personId: Long): Person = withContext(ioContext) {
        return@withContext database.personDAO().getPerson(personId)
    }

    suspend fun bookmarkPerson(personId: Long) = withContext(ioContext) {
        database.personDAO().bookmarkPerson(personId)
    }

    suspend fun unbookmarkPerson(personId: Long) = withContext(ioContext) {
        database.personDAO().unbookmarkPerson(personId)
    }

    fun searchPeople(query: String): List<Person> {
        return database.personDAO().searchPeople(query)
    }
}