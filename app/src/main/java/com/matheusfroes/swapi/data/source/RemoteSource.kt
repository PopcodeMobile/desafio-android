package com.matheusfroes.swapi.data.source

import com.google.gson.Gson
import com.matheusfroes.swapi.data.dto.BookmarkedEvent
import com.matheusfroes.swapi.data.mapper.PersonMapper
import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.extractIdFromUrl
import com.matheusfroes.swapi.network.ApiaryService
import com.matheusfroes.swapi.network.PeopleService
import com.matheusfroes.swapi.network.data.ApiaryFailureResponse
import com.matheusfroes.swapi.networkContext
import com.matheusfroes.swapi.parallelMap
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject

class RemoteSource @Inject constructor(
        private val peopleService: PeopleService,
        private val apiaryService: ApiaryService,
        private val gson: Gson
) {

    suspend fun getPeople(page: Int = 1): List<Person> = withContext(networkContext) {
        try {
            val getPeopleResponse = peopleService.getPeople(page).await()
            val peopleList = getPeopleResponse.results

            return@withContext PersonMapper.map(peopleList)
        } catch (e: Exception) {
            return@withContext listOf<Person>()
        }
    }

    private suspend fun getSpecie(specieUrl: String): String = withContext(networkContext) {
        val specieId = extractIdFromUrl(specieUrl)

        val specieResponse = peopleService.getSpecie(specieId).await()

        return@withContext specieResponse.name
    }

    suspend fun getSpecies(speciesUrl: List<String>): List<String> = withContext(networkContext) {
        return@withContext speciesUrl.parallelMap { url ->
            getSpecie(url)
        }
    }

    suspend fun getPlanet(planetUrl: String): String = withContext(networkContext) {
        val planetId = extractIdFromUrl(planetUrl)

        val planetResponse = peopleService.getPlanet(planetId).await()

        return@withContext planetResponse.name
    }

    suspend fun bookmarkPerson(personId: Long) = withContext(networkContext) {
        val bookmarkResponse = apiaryService.bookmarkPerson(personId).await()

        return@withContext if (bookmarkResponse.isSuccessful) {
            BookmarkedEvent(true, bookmarkResponse.body()?.message ?: "")
        } else {
            val error = bookmarkResponse.errorBody()?.string()
            val errorResponse = gson.fromJson(error, ApiaryFailureResponse::class.java)
            BookmarkedEvent(false, errorResponse.errorMessage)
        }
    }

}