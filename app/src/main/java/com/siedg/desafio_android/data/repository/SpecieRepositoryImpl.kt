package com.siedg.desafio_android.data.repository

import com.siedg.desafio_android.data.model.PersonModel
import com.siedg.desafio_android.data.model.Specie
import com.siedg.desafio_android.data.model.SpecieModel
import com.siedg.desafio_android.data.model.toModel
import com.siedg.desafio_android.data.repository.datasource.PersonCacheDataSource
import com.siedg.desafio_android.data.repository.datasource.PersonRemoteDataSource
import com.siedg.desafio_android.data.repository.datasource.SpecieCacheDataSource
import com.siedg.desafio_android.data.repository.datasource.SpecieRemoteDataSource
import com.siedg.desafio_android.domain.PersonRepository
import com.siedg.desafio_android.domain.SpecieRepository
import timber.log.Timber
import java.lang.Exception

class SpecieRepositoryImpl(
    private val specieRemoteDataSource: SpecieRemoteDataSource,
    private val specieCacheDataSource: SpecieCacheDataSource
) : SpecieRepository {
    override suspend fun getSpecieList(): List<SpecieModel>? = getSpecieListFromCache()

    private suspend fun getSpecieListFromCache(): List<SpecieModel> {
        lateinit var list: List<SpecieModel>
        try {
            list = specieCacheDataSource.getSpecieListFromCache()
        } catch (e: Exception) {
            Timber.e(e.message.toString())
        }
        if (list.isEmpty()) {
            list = getSpecieListFromAPI()
            specieCacheDataSource.saveSpecieListToCache(list)
        }
        return list
    }

    private suspend fun getSpecieListFromAPI(): List<SpecieModel> {
        val list: MutableList<SpecieModel> = mutableListOf()
        try {
            var index: Int? = 1
            while (index != null) {
                val response = specieRemoteDataSource.getSpeciesList(index)
                val body = response.body()
                body?.results?.forEach { specie ->
                    list.add(specie.toModel())
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