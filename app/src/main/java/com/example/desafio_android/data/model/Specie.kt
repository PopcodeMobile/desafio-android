package com.example.desafio_android.data.model

import com.google.gson.annotations.SerializedName

data class Specie(
    @SerializedName("name")
    val name: String
)