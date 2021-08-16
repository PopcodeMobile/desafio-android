package com.siedg.desafio_android.data.model

import com.google.gson.annotations.SerializedName

class PlanetModel (
    val climate: String,
    val diameter: String,
    val gravity: String,
    val name: String,
    val orbitalPeriod: String,
    val population: String,
    val residents: List<String>,
    val rotationPeriod: String,
    val surfaceWater: String,
    val terrain: String,
    val url: String
)