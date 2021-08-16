package com.siedg.desafio_android.data.model

import com.google.gson.annotations.SerializedName

data class Planet (
    @SerializedName("climate")
    val climate: String,
    @SerializedName("diameter")
    val diameter: String,
    @SerializedName("gravity")
    val gravity: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("orbital_period")
    val orbitalPeriod: String,
    @SerializedName("population")
    val population: String,
    @SerializedName("residents")
    val residents: List<String>,
    @SerializedName("rotation_period")
    val rotationPeriod: String,
    @SerializedName("surface_water")
    val surfaceWater: String,
    @SerializedName("terrain")
    val terrain: String,
    @SerializedName("url")
    val url: String


)

fun Planet.toModel(): PlanetModel {
    return PlanetModel(
        climate,
        diameter,
        gravity,
        name,
        orbitalPeriod,
        population,
        residents,
        rotationPeriod,
        surfaceWater,
        terrain,
        url
    )
}