package com.example.starwarsapi.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StarWarsApiResponse (
    @Expose @SerializedName ("count") val count:Int,
    @Expose @SerializedName ("next") val next: String,
    @Expose @SerializedName ("previous") val previous: String,
    @Expose @SerializedName ("results") val results: List<StarWarsResult>
    )

data class StarWarsResult(
    @Expose @SerializedName( "name") val name: String,
    @Expose @SerializedName( "gender") val gender: String,
    @Expose @SerializedName( "height") val heigth: String,
    @Expose @SerializedName( "mass") val mass: String,
    @Expose @SerializedName( "url") val url: String
)