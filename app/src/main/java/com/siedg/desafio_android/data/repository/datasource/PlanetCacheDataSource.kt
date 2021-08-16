package com.siedg.desafio_android.data.repository.datasource

import com.siedg.desafio_android.data.model.PlanetModel

interface PlanetCacheDataSource {
    suspend fun savePlanetListToCache(personList: List<PlanetModel>)

    suspend fun getPlanetListFromCache(): List<PlanetModel>
}