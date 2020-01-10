package br.com.starwarswiki.models

import com.google.gson.annotations.SerializedName

data class Specie(
    val id: Int = 0,
    @SerializedName("name") val name: String = ""
)