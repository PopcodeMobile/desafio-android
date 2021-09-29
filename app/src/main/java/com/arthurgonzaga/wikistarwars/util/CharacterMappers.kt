package com.arthurgonzaga.wikistarwars.util

import android.util.Log
import com.arthurgonzaga.wikistarwars.api.responses.CharacterResponse
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity

fun CharacterResponse.toEntity(
    homeWorldName: String,
    specieName: String?,
) = CharacterEntity(
    id = this.getId(),
    name= this.name,
    height= this.height,
    weight= this.weight,
    hairColor= this.hairColor,
    skinColor= this.skinColor,
    eyeColor= this.eyeColor,
    birthYear= this.birthYear,
    gender= this.gender,
    specieName = specieName ?: "Human",
    homeWoldName = homeWorldName,
)

fun CharacterResponse.toEntity(): CharacterEntity{
    Log.d("Mappers", "toEntity: id: ${this.getId()}")
    return CharacterEntity(
        id = this.getId(),
        name= this.name,
        height= this.height,
        weight= this.weight,
        hairColor= this.hairColor,
        skinColor= this.skinColor,
        eyeColor= this.eyeColor,
        birthYear= this.birthYear,
        gender= this.gender,
        specieName = null,
        homeWoldName = null,
    )
}