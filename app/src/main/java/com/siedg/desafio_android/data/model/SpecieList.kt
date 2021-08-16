package com.siedg.desafio_android.data.model

import com.google.gson.annotations.SerializedName

data class SpecieList (
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<Specie>
)

fun SpecieList.mapToModel(): List<SpecieModel> {
    return this.results.map { value -> value.toModel() }
}
