package com.siedg.desafio_android.data.model

import com.google.gson.annotations.SerializedName

data class PersonList(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<Person>
)

fun PersonList.mapToModel(): List<PersonModel> {
    return this.results.map { value -> value.toModel() }
}