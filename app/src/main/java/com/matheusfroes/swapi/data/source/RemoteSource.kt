package com.matheusfroes.swapi.data.source

import com.matheusfroes.swapi.NextPageUrl
import com.matheusfroes.swapi.data.mapper.PersonMapper
import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.extractIdFromUrl
import com.matheusfroes.swapi.network.ApiaryService
import com.matheusfroes.swapi.network.PeopleService
import com.matheusfroes.swapi.networkContext
import com.matheusfroes.swapi.parallelMap
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject

class RemoteSource @Inject constructor(
        private val peopleService: PeopleService,
        private val apiaryService: ApiaryService
) {

    suspend fun getPeople(page: Int): Pair<NextPageUrl, List<Person>> = withContext(networkContext) {
        val getPeopleResponse = peopleService.getPeople(page).await()
        val nextPageUrl = getPeopleResponse.next

        val peopleList = getPeopleResponse.results

        val people = peopleList.parallelMap { peopleResponse ->
            // Obtendo species e homeworld assincronamente
            val species = async { peopleResponse.species.map { getSpecie(it) } }
            val homeworld = async { getPlanet(peopleResponse.homeworld) }

            return@parallelMap PersonMapper.map(peopleResponse, species.await(), homeworld.await())
        }

        return@withContext Pair(nextPageUrl, people)
    }

    private suspend fun getSpecie(specieUrl: String): String = withContext(networkContext) {
        val specieId = extractIdFromUrl(specieUrl)

        val specieResponse = peopleService.getSpecie(specieId).await()

        return@withContext specieResponse.name
    }

    private suspend fun getPlanet(planetUrl: String): String = withContext(networkContext) {
        val planetId = extractIdFromUrl(planetUrl)

        val planetResponse = peopleService.getPlanet(planetId).await()

        return@withContext planetResponse.name
    }

    fun bookmarkPerson(personId: Long) {
        apiaryService.bookmarkPerson(personId)

        // TODO Handle different responses
    }

}