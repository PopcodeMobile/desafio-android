package com.siedg.desafio_android.data.model

import com.google.gson.annotations.SerializedName

data class Specie (
    @SerializedName("average_height")
    val averageHeight: String,
    @SerializedName("average_lifespan")
    val averageLifespan: String,
    @SerializedName("classification")
    val classification: String,
    @SerializedName("created")
    val created: String,
    @SerializedName("designation")
    val designation: String,
    @SerializedName("edited")
    val edited: String,
    @SerializedName("eye_color")
    val eyeColor: String,
    @SerializedName("hair_color")
    val hairColor: String,
    @SerializedName("homeworld")
    val homeworld: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("people")
    val people: List<String>,
    @SerializedName("films")
    val films: List<String>,
    @SerializedName("skin_colors")
    val skinColor: String,
    @SerializedName("url")
    val url: String
)

fun Specie.toModel(): SpecieModel {
    return SpecieModel(
        averageHeight,
        averageLifespan,
        classification,
        created,
        designation,
        edited,
        eyeColor,
        hairColor,
        homeworld,
        language,
        name,
        people,
        films,
        skinColor,
        url
    )
}