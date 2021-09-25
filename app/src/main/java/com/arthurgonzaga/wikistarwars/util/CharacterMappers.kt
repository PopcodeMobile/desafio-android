package com.arthurgonzaga.wikistarwars.util

import com.arthurgonzaga.wikistarwars.api.responses.CharacterResponse
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity

fun CharacterResponse.toEntity(
    specieName: String,
    homeWorldName: String,
) = CharacterEntity(
    id = this.id,
    height= this.height,
    weight= this.weight,
    hairColor= this.hairColor,
    skinColor= this.skinColor,
    eyeColor= this.eyeColor,
    birthYear= this.birthYear,
    gender= this.gender,
    specieName = specieName,
    homeWoldName = homeWorldName,
)

fun CharacterResponse.toEntity() = CharacterEntity(
    id = this.id,
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