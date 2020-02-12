package com.albuquerque.starwarswiki.app.repository

import com.albuquerque.starwarswiki.app.model.dto.Species
import com.albuquerque.starwarswiki.app.model.dto.Planet
import com.albuquerque.starwarswiki.app.model.dto.ResponseFavorite
import com.albuquerque.starwarswiki.core.network.WikiResult
import com.albuquerque.starwarswiki.app.model.dto.ResponsePeople

interface IWikiRemoteDataSource {

    suspend fun getPeople(page: Int): WikiResult<ResponsePeople>
    suspend fun favorite(): WikiResult<ResponseFavorite>
    suspend fun search(value: String): WikiResult<ResponsePeople>
    suspend fun getHomePlanet(id: String): WikiResult<Planet>
    suspend fun getSpecies(id: String): WikiResult<Species>

}