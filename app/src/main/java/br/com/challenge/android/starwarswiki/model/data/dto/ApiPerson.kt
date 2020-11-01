package br.com.challenge.android.starwarswiki.model.data.dto

import com.google.gson.annotations.SerializedName

data class ApiPerson(
        @SerializedName("name")
        val name: String,
        @SerializedName("birth_year")
        val birthYear: String,
        @SerializedName("eye_color")
        val eyeColor: String,
        @SerializedName("gender")
        val gender: String,
        @SerializedName("hair_color")
        val hairColor: String,
        @SerializedName("height")
        val height: String,
        @SerializedName("mass")
        val mass: String,
        @SerializedName("skin_color")
        val skinColor: String,
        @SerializedName("homeworld")
        val homeWorld: String,
        @SerializedName("species")
        val species: ArrayList<String>
)