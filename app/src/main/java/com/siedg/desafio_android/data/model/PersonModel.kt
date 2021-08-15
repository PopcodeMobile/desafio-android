package com.siedg.desafio_android.data.model

data class PersonModel (
    val name: String,
    val height: Double,
    val mass: Double,
    val hairColor: String,
    val skinColor: String,
    val eyeColor: String,
    val birthYear: String,
    val gender: String,
    val homeworld: String,
    val films: List<String>,
    val species: List<String>,
    val vehicles: List<String>,
    val starships: List<String>,
    val created: String,
    val edited: String,
    val url: String,
    val isFavorite: Boolean
)