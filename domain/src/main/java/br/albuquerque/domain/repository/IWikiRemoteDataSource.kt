package br.albuquerque.domain.repository

import br.albuquerque.core.network.WikiResult
import br.albuquerque.data.dto.Species
import br.albuquerque.data.dto.Planet
import br.albuquerque.data.dto.ResponseFavorite
import br.albuquerque.data.dto.ResponsePeople

interface IWikiRemoteDataSource {

    suspend fun getPeople(page: Int): WikiResult<ResponsePeople>
    suspend fun favorite(): WikiResult<ResponseFavorite>
    suspend fun search(value: String): WikiResult<ResponsePeople>
    suspend fun getHomePlanet(id: String): WikiResult<Planet>
    suspend fun getSpecies(id: String): WikiResult<Species>

}