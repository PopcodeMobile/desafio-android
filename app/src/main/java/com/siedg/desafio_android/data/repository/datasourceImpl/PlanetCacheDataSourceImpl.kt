package com.siedg.desafio_android.data.repository.datasourceImpl

import com.siedg.desafio_android.data.model.PlanetModel
import com.siedg.desafio_android.data.repository.datasource.PlanetCacheDataSource

class PlanetCacheDataSourceImpl : PlanetCacheDataSource {
    private var list = ArrayList<PlanetModel>()
    override suspend fun savePlanetListToCache(planetList: List<PlanetModel>) {
        list.clear()
        list = ArrayList(planetList)
    }

    override suspend fun getPlanetListFromCache(): List<PlanetModel> {
        return list
    }
}