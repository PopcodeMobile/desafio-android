package com.siedg.desafio_android.domain

import com.siedg.desafio_android.data.model.PlanetModel

interface PlanetRepository {
    suspend fun getPlanetList(): List<PlanetModel>?
}