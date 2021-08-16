package com.siedg.desafio_android.data.repository.datasourceImpl

import com.siedg.desafio_android.data.model.PersonModel
import com.siedg.desafio_android.data.model.SpecieModel
import com.siedg.desafio_android.data.repository.datasource.SpecieCacheDataSource

class SpecieCacheDataSourceImpl : SpecieCacheDataSource {
    private var list = ArrayList<SpecieModel>()
    override suspend fun saveSpecieListToCache(specieList: List<SpecieModel>) {
        list.clear()
        list = ArrayList(specieList)
    }

    override suspend fun getSpecieListFromCache(): List<SpecieModel> {
        return list
    }
}