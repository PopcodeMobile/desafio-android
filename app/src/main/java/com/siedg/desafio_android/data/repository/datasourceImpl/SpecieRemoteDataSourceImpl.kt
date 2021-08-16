package com.siedg.desafio_android.data.repository.datasourceImpl

import com.siedg.desafio_android.data.api.ApiService
import com.siedg.desafio_android.data.model.SpecieList
import com.siedg.desafio_android.data.repository.datasource.SpecieRemoteDataSource
import retrofit2.Response

class SpecieRemoteDataSourceImpl (
    private val service: ApiService,
    ): SpecieRemoteDataSource {
        override suspend fun getSpeciesList(index: Int): Response<SpecieList> = service.getSpeciesList(index)
}