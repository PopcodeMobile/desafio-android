package com.matheussilas97.starwarsapp.api.response

import com.google.gson.annotations.SerializedName

data class CharactersDetailsResponse(

    @SerializedName("url")
    val url: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("height")
    val height: String,

    @SerializedName("mass")
    val mass: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("hair_color")
    val hairColor: String,

    @SerializedName("skin_color")
    val skinColor: String,

    @SerializedName("eye_color")
    val eyeColor: String,

    @SerializedName("birth_year")
    val birthYear: String,

    @SerializedName("homeworld")
    val homeworld: String,

    @SerializedName("films")
    val films: List<String>,

    @SerializedName("vehicles")
    val vehicles: List<String>,

    @SerializedName("starships")
    val starships: List<String>

)