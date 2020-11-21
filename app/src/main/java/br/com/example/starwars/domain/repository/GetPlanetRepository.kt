package br.com.example.starwars.domain.repository

import br.com.example.starwars.domain.entities.Planet

interface GetPlanetRepository {
    suspend fun getPlanet(url: String): Planet
}