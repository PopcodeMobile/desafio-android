package com.matheusfroes.swapi.network.data

import com.google.gson.annotations.SerializedName

data class PersonResponse(
        val name: String,
        val height: String,
        val mass: String,
        @SerializedName("hair_color")
        val hairColor: String,
        val skinColor: String,
        val eyeColor: String,
        val birthYear: String,
        val gender: String,
        val homeworld: String,
        val species: List<String>,
        val url: String
)