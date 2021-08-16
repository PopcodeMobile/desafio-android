package com.siedg.desafio_android.domain

import com.siedg.desafio_android.data.model.PlanetModel

class GetPlanetListUseCase(private val planetRepository: PlanetRepository) {
    suspend fun execute(): List<PlanetModel>? = planetRepository.getPlanetList()
}