package com.siedg.desafio_android.data.repository.datasource

import com.siedg.desafio_android.data.model.Person
import com.siedg.desafio_android.data.model.PersonModel

interface PersonCacheDataSource {
    suspend fun savePersonListToCache(personList: List<PersonModel>)

    suspend fun getPersonListFromCache(): List<PersonModel>
}