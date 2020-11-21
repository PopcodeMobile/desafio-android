package br.com.example.starwars.domain.entities

import java.io.Serializable

data class People(
    val id: Int? = null,
    val gender: String? = null,
    val height: String? = null,
    val mass: String? = null,
    val name: String? = null,
    val url: String? = null,
    val hairColor: String? = null,
    val skinColor: String? = null,
    val eyeColor: String? = null,
    val birthYear: String? = null,
    val homeWorld: String? = null,
    val species: List<String>? = null,
    var favorite: Boolean = false
) : Serializable