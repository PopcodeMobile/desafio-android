package com.siedg.desafio_android.data.repository.datasource

import com.siedg.desafio_android.data.model.PersonList
import com.siedg.desafio_android.data.model.SpecieModel
import retrofit2.Response

class SpecieRemoteDataSource {
    suspend fun getSpecies(): Response<SpecieModel>
}