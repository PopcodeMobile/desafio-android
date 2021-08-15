package com.siedg.desafio_android.data.repository.datasource

import com.siedg.desafio_android.data.model.Person
import com.siedg.desafio_android.data.model.PersonList
import retrofit2.Response

interface PersonRemoteDataSource {
    suspend fun getPersonList(index: Int): Response<PersonList>
}