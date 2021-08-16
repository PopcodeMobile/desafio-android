package com.siedg.desafio_android.data.model

import com.google.gson.annotations.SerializedName

data class PlanetList(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<Planet>
)

fun PlanetList.mapToModel(): List<PlanetModel> {
    return this.results.map { value -> value.toModel() }
}