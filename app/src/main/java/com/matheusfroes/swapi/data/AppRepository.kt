package com.matheusfroes.swapi.data

import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.data.source.LocalSource
import com.matheusfroes.swapi.data.source.RemoteSource
import javax.inject.Inject

class AppRepository @Inject constructor(
        private val local: LocalSource,
        private val remote: RemoteSource) {

    suspend fun getPeople(): List<Person> {
        return local.getPeople()
    }

    suspend fun fetchPeople(page: Int) {
        val people = remote.getPeople(page)
        local.savePeople(people)
    }

    suspend fun getPerson(personId: Long): Person {
        return local.getPerson(personId)
    }

    suspend fun bookmarkPerson(personId: Long) {
        local.bookmarkPerson(personId)

        // TODO Send bookmark request to Apiary
    }

    suspend fun unbookmarkPerson(personId: Long) {
        local.unbookmarkPerson(personId)
    }
}