package com.siedg.desafio_android.data.repository

import com.siedg.desafio_android.data.model.Person
import com.siedg.desafio_android.data.model.PersonList
import com.siedg.desafio_android.data.model.PersonModel
import com.siedg.desafio_android.data.model.toModel
import com.siedg.desafio_android.data.repository.datasource.PersonCacheDataSource
import com.siedg.desafio_android.data.repository.datasource.PersonRemoteDataSource
import com.siedg.desafio_android.domain.PersonRepository
import timber.log.Timber
import java.lang.Exception

class PersonRepositoryImpl(
    private val personRemoteDataSource: PersonRemoteDataSource,
    private val personCacheDataSource: PersonCacheDataSource
) : PersonRepository {
    override suspend fun getPersonList(): List<PersonModel>? = getPersonListFromCache()

    private suspend fun getPersonListFromCache(): List<PersonModel> {
        lateinit var list: List<PersonModel>
        try {
            list = personCacheDataSource.getPersonListFromCache()
        } catch (e: Exception) {
            Timber.e(e.message.toString())
        }
        if (list.isEmpty()) {
            list = getPersonListFromAPI()
            personCacheDataSource.savePersonListToCache(list)
        }
        return list
    }

    private suspend fun getPersonListFromAPI(): List<PersonModel> {
        val list: MutableList<PersonModel> = mutableListOf()
        try {
            var index: Int? = 1
            while (index != null) {
                val response = personRemoteDataSource.getPersonList(index)
                val body = response.body()
                body?.results?.forEach { person ->
                    list.add(person.toModel())
                }
                if (body?.next.isNullOrEmpty()) {
                    index = null
                } else {
                    index++
                }
            }
        } catch (e: Exception) {
            Timber.e(e.message.toString())
        }
        return list
    }
}