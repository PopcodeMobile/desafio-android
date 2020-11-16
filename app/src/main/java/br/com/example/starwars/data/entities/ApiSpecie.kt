package br.com.example.starwars.data.entities

import br.com.example.starwars.domain.entities.Specie
import com.google.gson.annotations.SerializedName

data class ApiSpecie(
    @SerializedName("name")
    val name: String? = null
) {
    fun apiSpecieToSpecie(apiSpecie: ApiSpecie): Specie {
        return Specie(
            name = apiSpecie.name
        )
    }
}