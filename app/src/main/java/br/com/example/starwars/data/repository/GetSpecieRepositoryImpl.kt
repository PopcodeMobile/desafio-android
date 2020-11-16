package br.com.example.starwars.data.repository

import br.com.example.starwars.data.remote.ApiService
import br.com.example.starwars.domain.entities.Specie
import br.com.example.starwars.domain.repository.GetSpecieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSpecieRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : GetSpecieRepository {
    override suspend fun getSpecie(url: String): Specie {
        return withContext(Dispatchers.IO) {
            apiService.getSpecie(url).let { it.apiSpecieToSpecie(it) }
        }
    }
}