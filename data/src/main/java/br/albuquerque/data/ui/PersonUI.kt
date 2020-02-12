package br.albuquerque.data.ui

import java.io.Serializable

data class PersonUI(
    var name: String,
    var height: String,
    var mass: String,
    var hairColor: String,
    var skinColor: String,
    var eyeColor: String,
    var birthYear: String,
    var gender: String,
    var homeworld: String,
    var isFavorite: Boolean,
    var species: ArrayList<String>? = arrayListOf(),
    var tryAgainPosition: Int? = null
): Serializable