package com.example.starwarswiki.domain

data class PersonModel(
    val url: String,
    val id: Int,
    val name: String,
    val height: String,
    val mass: String,
    val hair_color: String,
    val skin_color: String,
    val eye_color: String,
    val birth_year: String,
    val gender: String,
    val homeworld: String,
    val species: List<String>
)