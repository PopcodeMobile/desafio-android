package com.siedg.desafio_android.data.repository

import com.siedg.desafio_android.data.model.PlanetModel
import com.siedg.desafio_android.data.model.toModel
import com.siedg.desafio_android.data.repository.datasource.PlanetCacheDataSource
import com.siedg.desafio_android.data.repository.datasource.PlanetRemoteDataSource
import com.siedg.desafio_android.domain.PlanetRepository
import timber.log.Timber
import java.lang.Exception

class PlanetRepositoryImpl(
    private val planetRemoteDataSource: PlanetRemoteDataSource,
    private val planetCacheDataSource: PlanetCacheDataSource
) : PlanetRepository {
    override suspend fun getPlanetList(): List<PlanetModel>? = getPlanetListFromCache()

    private suspend fun getPlanetListFromCache(): List<PlanetModel> {
        lateinit var list: List<PlanetModel>
        try {
            list = planetCacheDataSource.getPlanetListFromCache()
        } catch (e: Exception) {
            Timber.e(e.message.toString())
        }
        if (list.isEmpty()) {
            list = getPlanetListFromAPI()
            planetCacheDataSource.savePlanetListToCache(list)
        }
        return list
    }

    private suspend fun getPlanetListFromAPI(): List<PlanetModel> {
        val list: MutableList<PlanetModel> = mutableListOf()
        try {
            var index: Int? = 1
            while (index != null) {
                val response = planetRemoteDataSource.getPlanetsList(index)
                val body = response.body()
                body?.results?.forEach { planet ->
                    list.add(planet.toModel())
                }
                if (body?.next.isNullOrEmpty()) {
                    index = null
                } else {
                    index++
                }
            }
        } catch (e: Exception) {
            Timber.e(e.message.toString())
        }
        return list
    }
}
