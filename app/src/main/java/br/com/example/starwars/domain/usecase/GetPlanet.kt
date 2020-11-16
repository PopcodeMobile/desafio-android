package br.com.example.starwars.domain.usecase

import br.com.example.starwars.domain.repository.GetPlanetRepository
import javax.inject.Inject

class GetPlanet @Inject constructor(
    private val repository: GetPlanetRepository
) {

    suspend fun execute(url: String) = repository.getPlanet(url)
}