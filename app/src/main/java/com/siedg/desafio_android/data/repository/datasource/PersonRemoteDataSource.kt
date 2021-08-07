package com.siedg.desafio_android.data.repository.datasource

import com.siedg.desafio_android.data.model.Person
import retrofit2.Response

interface PersonRemoteDataSource {
    suspend fun getPerson(index: Int): Response<Person>
}