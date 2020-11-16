package br.com.example.starwars.data.repository

import br.com.example.starwars.data.remote.ApiService
import br.com.example.starwars.domain.entities.Planet
import br.com.example.starwars.domain.repository.GetPlanetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPlanetRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : GetPlanetRepository {
    override suspend fun getPlanet(url: String): Planet {
        return withContext(Dispatchers.IO) {
            apiService.getPlanet(url).let { it.apiPlanetToPlanet(it) }
        }
    }
}