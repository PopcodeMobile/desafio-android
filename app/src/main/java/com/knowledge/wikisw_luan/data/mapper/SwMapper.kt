package com.knowledge.wikisw_luan.data.mapper

import com.knowledge.wikisw_luan.data.cache.CharacterEntity
import com.knowledge.wikisw_luan.data.models.CharacterResponse
import com.knowledge.wikisw_luan.models.CharacterModel

object SwMapper {

    fun characterResponseToEntity(response: List<CharacterResponse>, planet: String, specie: String, isFavorite: Boolean): List<CharacterEntity> {
        return response.map { characterResponseToEntity(it, "", "", false) }

    }

    fun characterResponseToEntity(response: CharacterResponse, planet: String, specie: String, isFavorite: Boolean): CharacterEntity {
        return CharacterEntity(
            name = response.name,
            mass = response.mass,
            height = response.height,
            hairColor = response.hairColor,
            skinColor = response.skinColor,
            eyeColor = response.eyeColor,
            birthYear = response.eyeColor,
            gender = response.gender,
            homeworldId = planet,
            specieId = specie,
            cid = response.name,
            isFavorite = isFavorite

        )

    }

    fun characterResponseToModel(response: List<CharacterResponse>, planet: String, specie: String) : List<CharacterModel> {
       return response.map { characterResponseToModel(it, "", "") }
    }

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