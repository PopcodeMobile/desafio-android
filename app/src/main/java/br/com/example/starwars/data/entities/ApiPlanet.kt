package br.com.example.starwars.data.entities

import br.com.example.starwars.domain.entities.Planet
import com.google.gson.annotations.SerializedName

data class ApiPlanet(
    @SerializedName("name")
    val name: String? = null
) {
    fun apiPlanetToPlanet(apiPlanet: ApiPlanet): Planet {
        return Planet(
            name = apiPlanet.name
        )
    }
}