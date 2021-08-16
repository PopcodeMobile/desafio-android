package com.siedg.desafio_android.data.model

import com.google.gson.annotations.SerializedName

class SpecieModel(
    val averageHeight: String,
    val averageLifespan: String,
    val classification: String,
    val created: String,
    val designation: String,
    val edited: String,
    val eyeColor: String,
    val hairColor: String,
    val homeworld: String,
    val language: String,
    val name: String,
    val people: List<String>,
    val films: List<String>,
    val skinColor: String,
    val url: String
)