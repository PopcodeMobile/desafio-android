package com.matheusfroes.swapi.data.source

import com.matheusfroes.swapi.data.AppDatabase
import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.ioContext
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject

class LocalSource @Inject constructor(
        private val database: AppDatabase
) {

    suspend fun savePeople(people: List<Person>) = withContext(ioContext) {
        database.personDAO().insert(people)
    }

    suspend fun getPerson(personId: Long): Person = withContext(ioContext) {
        return@withContext database.personDAO().getPerson(personId)
    }

    suspend fun getBookmarkedPeople() = withContext(ioContext) {
        return@withContext database.personDAO().getBookmarkedPeople()
    }

    suspend fun bookmarkPerson(personId: Long) = withContext(ioContext) {
        database.personDAO().bookmarkPerson(personId)
    }

    suspend fun unbookmarkPerson(personId: Long) = withContext(ioContext) {
        database.personDAO().unbookmarkPerson(personId)
    }
}