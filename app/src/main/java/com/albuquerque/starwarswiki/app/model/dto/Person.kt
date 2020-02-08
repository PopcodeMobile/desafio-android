package com.albuquerque.starwarswiki.app.model.dto

import com.google.gson.annotations.SerializedName

data class Person(
    val name: String? = null,
    val height: String? = null,
    val mass: String? = null,
    @SerializedName("hair_color") val hairColor: String? = null,
    @SerializedName("skin_color") val skinColor: String? = null,
    @SerializedName("eye_color") val eyeColor: String? = null,
    @SerializedName("birth_year") val birthYear: String? = null,
    val gender: String? = null,
    val homeworld: String? = null
)