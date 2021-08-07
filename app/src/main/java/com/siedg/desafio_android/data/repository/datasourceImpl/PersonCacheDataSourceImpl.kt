package com.siedg.desafio_android.data.repository.datasourceImpl

import com.siedg.desafio_android.data.model.Person
import com.siedg.desafio_android.data.repository.datasource.PersonCacheDataSource

class PersonCacheDataSourceImpl : PersonCacheDataSource {
    private var list = ArrayList<Person>()
    override suspend fun savePersonListToCache(personList: List<Person>) {
        list.clear()
        list = ArrayList(personList)
    }

    override suspend fun getPersonListFromCache(): List<Person> {
        return list
    }

}