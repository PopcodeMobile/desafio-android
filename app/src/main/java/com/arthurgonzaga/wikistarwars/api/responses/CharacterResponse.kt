package com.arthurgonzaga.wikistarwars.api.responses


import android.util.Log
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity
import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    val name: String = "",
    val height: String = "",
    @SerializedName("mass")
    val weight: String = "",
    @SerializedName("hair_color")
    val hairColor: String = "",
    @SerializedName("skin_color")
    val skinColor: String = "",
    @SerializedName("eye_color")
    val eyeColor: String = "",
    @SerializedName("birth_year")
    val birthYear: String = "",
    val gender: String = "",
    @SerializedName("homeworld")
    private val homeWorldUrl: String = "",
    @SerializedName("species")
    private val speciesUrls: List<String> = listOf(),
    private val url: String
) {

    fun getId(): Int{
        return url.split("people/")[1].replace("/","").toInt()
    }

    fun getHomeWorldId(): Int{
        return homeWorldUrl.split("planets/")[1].replace("/","").toInt()
    }

    fun getSpecieId(): Int? {
        return speciesUrls.firstOrNull()?.split("species/")?.get(1)?.replace("/","")?.toIntOrNull()
    }

}