package com.siedg.desafio_android.data.repository.datasource

import com.siedg.desafio_android.data.model.PersonList
import com.siedg.desafio_android.data.model.Specie
import com.siedg.desafio_android.data.model.SpecieList
import retrofit2.Response

interface SpecieRemoteDataSource {
    suspend fun getSpeciesList(index: Int): Response<SpecieList>
}