package com.knowledge.wikisw_luan.data.mapper

import com.knowledge.wikisw_luan.data.models.CharacterResponse
import com.knowledge.wikisw_luan.models.CharacterModel

object SwMapper {
    fun characterResponseToModel(response: CharacterResponse, planet: String, specie: String) : CharacterModel {
        return CharacterModel(
            name = response.name,
            mass = response.mass,
            height = response.height,
            hairColor = response.hairColor,
            skinColor = response.skinColor,
            eyeColor = response.eyeColor,
            birthYear = response.eyeColor,
            gender = response.gender,
            homeWorld = planet,
            species = specie
        )
    }
}