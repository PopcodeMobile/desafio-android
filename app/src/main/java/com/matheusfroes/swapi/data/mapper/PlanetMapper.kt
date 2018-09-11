package com.matheusfroes.swapi.data.mapper

import com.matheusfroes.swapi.data.model.Planet
import com.matheusfroes.swapi.network.data.PlanetResponse

class PlanetMapper {
    companion object {
        fun map(planetId: Long, planetResponse: PlanetResponse): Planet {
            return Planet(
                    id = planetId,
                    name = planetResponse.name
            )
        }
    }
}