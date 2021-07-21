package com.example.starwarsapi.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Starwars (
    @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("name") val name: String,
    @Expose @SerializedName("gender") val gender: String,
    @Expose @SerializedName("mass") val mass: String,
    @Expose @SerializedName("hair_color") val hair_color: String,
    @Expose @SerializedName("skin_color") val skin_color: String,
    @Expose @SerializedName("eye_color") val eye_color: String,
    @Expose @SerializedName("birth_year") val birth_year: String,
    @Expose @SerializedName("height") val height: String
        )
