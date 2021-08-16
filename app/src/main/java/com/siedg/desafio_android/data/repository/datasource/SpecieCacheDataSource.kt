package com.siedg.desafio_android.data.repository.datasource

import com.siedg.desafio_android.data.model.SpecieModel

interface SpecieCacheDataSource {
    suspend fun saveSpecieListToCache(personList: List<SpecieModel>)

    suspend fun getSpecieFromCache(): List<SpecieModel>
}