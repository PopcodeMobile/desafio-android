package com.siedg.desafio_android.data.repository.datasourceImpl

import com.siedg.desafio_android.data.api.ApiService
import com.siedg.desafio_android.data.model.PlanetList
import com.siedg.desafio_android.data.repository.datasource.PlanetRemoteDataSource
import retrofit2.Response

class PlanetRemoteDataSourceImpl(
    private val service: ApiService,
): PlanetRemoteDataSource {
        override suspend fun getPlanetsList(index: Int): Response<PlanetList> = service.getPlanetsList(index)
}