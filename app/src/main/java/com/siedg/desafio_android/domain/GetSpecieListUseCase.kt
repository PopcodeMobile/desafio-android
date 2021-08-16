package com.siedg.desafio_android.domain

import com.siedg.desafio_android.data.model.SpecieModel

class GetSpecieListUseCase(private val specieRepository: SpecieRepository) {
    suspend fun execute(): List<SpecieModel>? = specieRepository.getSpecieList()
}