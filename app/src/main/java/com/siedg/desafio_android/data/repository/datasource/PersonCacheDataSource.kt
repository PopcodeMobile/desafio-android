package com.siedg.desafio_android.data.repository.datasource

import com.siedg.desafio_android.data.model.Person

interface PersonCacheDataSource {
    suspend fun savePersonListToCache(personList: List<Person>)

    suspend fun getPersonListFromCache(): List<Person>
}