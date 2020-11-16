package br.com.example.starwars.domain.repository

import br.com.example.starwars.domain.entities.Specie

interface GetSpecieRepository {
    suspend fun getSpecie(url: String): Specie
}