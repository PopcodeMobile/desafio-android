package com.siedg.desafio_android.domain

import com.siedg.desafio_android.data.model.Person
import com.siedg.desafio_android.data.model.PersonList
import com.siedg.desafio_android.data.model.PersonModel

interface PersonRepository {
    suspend fun getPersonList(): List<PersonModel>?
}