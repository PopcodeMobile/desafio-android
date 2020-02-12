package com.albuquerque.starwarswiki.app.repository

import com.albuquerque.starwarswiki.app.model.dto.Species
import com.albuquerque.starwarswiki.app.model.dto.Planet
import com.albuquerque.starwarswiki.app.model.dto.ResponseFavorite
import com.albuquerque.starwarswiki.core.network.WikiResult
import com.albuquerque.starwarswiki.core.repository.WikiRemoteRepository
import com.albuquerque.starwarswiki.app.model.dto.ResponsePeople
import kotlin.random.Random

class WikiRemoteRepository : WikiRemoteRepository(), IWikiRemoteDataSource {

    private val API by lazy { getRetrofitBuilder().create(WikiAPI::class.java) }
    private val API_FAVORITE by lazy {
        getRetrofitBuilder("https://private-782d3-starwarsfavorites.apiary-mock.com/").create(
            WikiAPI::class.java
        )
    }

    override suspend fun getPeople(page: Int): WikiResult<ResponsePeople> {
        return executeRequest(API) { fetchPeople(page) }
    }

    override suspend fun favorite(): WikiResult<ResponseFavorite> {

        return executeRequest(API_FAVORITE) {
            if (Random.nextBoolean())
                favorite()
            else
                favorite("status=400")
        }
    }

    override suspend fun search(value: String): WikiResult<ResponsePeople> {
        return executeRequest(API) { search(value) }
    }

    override suspend fun getHomePlanet(id: String): WikiResult<Planet> {
        return executeRequest(API) { fetchHomePlanet(id) }
    }

    override suspend fun getSpecies(id: String): WikiResult<Species> {
        return executeRequest(API) { fetchSpecies(id) }
    }

}