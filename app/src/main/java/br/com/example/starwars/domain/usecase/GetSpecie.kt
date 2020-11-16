package br.com.example.starwars.domain.usecase

import br.com.example.starwars.domain.repository.GetSpecieRepository
import javax.inject.Inject

class GetSpecie @Inject constructor(
    private val repository: GetSpecieRepository
) {

    suspend fun execute(url: String) = repository.getSpecie(url)
}