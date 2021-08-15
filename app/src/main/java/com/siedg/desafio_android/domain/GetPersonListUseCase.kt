package com.siedg.desafio_android.domain

import com.siedg.desafio_android.data.model.Person

class GetPersonListUseCase(private val personRepository: PersonRepository) {
    suspend fun execute():List<Person>? = personRepository.getPersonList()
}