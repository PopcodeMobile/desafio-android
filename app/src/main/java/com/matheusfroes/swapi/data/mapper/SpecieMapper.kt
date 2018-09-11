package com.matheusfroes.swapi.data.mapper

import com.matheusfroes.swapi.data.model.Specie
import com.matheusfroes.swapi.network.data.SpecieResponse

class SpecieMapper {
    companion object {
        fun map(specieId: Long, specieResponse: SpecieResponse): Specie {
            return Specie(
                    id = specieId,
                    name = specieResponse.name
            )
        }
    }
}