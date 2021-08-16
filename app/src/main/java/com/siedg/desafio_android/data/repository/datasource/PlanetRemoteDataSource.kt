package com.siedg.desafio_android.data.repository.datasource

import com.siedg.desafio_android.data.model.PlanetList
import retrofit2.Response

interface PlanetRemoteDataSource {
    suspend fun getPlanetsList(index: Int): Response<PlanetList>
}