package com.siedg.desafio_android.domain

import com.siedg.desafio_android.data.model.PersonModel

class GetPersonListUseCase(private val personRepository: PersonRepository) {
    suspend fun execute():List<PersonModel>? = personRepository.getPersonList()
}