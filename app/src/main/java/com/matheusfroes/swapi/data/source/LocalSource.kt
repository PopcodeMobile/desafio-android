package com.matheusfroes.swapi.data.source

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import com.matheusfroes.swapi.data.AppDatabase
import com.matheusfroes.swapi.data.model.PendingBookmark
import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.extra.ioContext
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject

class LocalSource @Inject constructor(
        private val database: AppDatabase
) {

    fun deletePeople() {
        database.personDAO().deletePeople()
    }

    fun getPeople(): DataSource.Factory<Int, Person> {
        return database.personDAO().getPeople()
    }

    fun searchPeople(query: String): DataSource.Factory<Int, Person> {
        return database.personDAO().searchPeople(query)
    }

    fun getBookmarkedPeople(): LiveData<List<Person>> {
        return database.personDAO().getBookmarkedPeople()
    }

    suspend fun savePeople(people: List<Person>) = withContext(ioContext) {
        database.personDAO().insert(people)
    }

    suspend fun getPerson(personId: Int): Person = withContext(ioContext) {
        return@withContext database.personDAO().getPerson(personId)
    }

    suspend fun bookmarkPerson(personId: Int) = withContext(ioContext) {
        database.personDAO().bookmarkPerson(personId)
    }

    suspend fun unbookmarkPerson(personId: Int) = withContext(ioContext) {
        database.personDAO().unbookmarkPerson(personId)
    }

    suspend fun getPendingBookmarks(): List<PendingBookmark> = withContext(ioContext) {
        return@withContext database.personDAO().getPendingBookmarks()
    }

    suspend fun addPendingBookmark(personId: Int) = withContext(ioContext) {
        database.personDAO().addPendingBookmark(PendingBookmark(personId))
    }

    suspend fun removePendingBookmark(personId: Int) = withContext(ioContext) {
        database.personDAO().removePendingBookmark(personId)
    }
}