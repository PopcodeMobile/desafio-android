package com.siedg.desafio_android.data.repository.datasourceImpl

import com.siedg.desafio_android.data.model.Person
import com.siedg.desafio_android.data.model.PersonModel
import com.siedg.desafio_android.data.repository.datasource.PersonCacheDataSource

class PersonCacheDataSourceImpl : PersonCacheDataSource {
    private var list = ArrayList<PersonModel>()
    override suspend fun savePersonListToCache(personList: List<PersonModel>) {
        list.clear()
        list = ArrayList(personList)
    }

    override suspend fun getPersonListFromCache(): List<PersonModel> {
        return list
    }
}