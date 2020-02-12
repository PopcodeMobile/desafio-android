package br.albuquerque.data.util

import br.albuquerque.data.dto.Species
import br.albuquerque.data.dto.Person
import br.albuquerque.data.dto.Planet
import br.albuquerque.data.dto.ResponsePeople
import br.albuquerque.data.entity.ConfigEntity
import br.albuquerque.data.entity.PersonEntity
import br.albuquerque.data.ui.SpeciesUI
import br.albuquerque.data.ui.PersonUI
import br.albuquerque.data.ui.PlanetUI

fun Person.toEntity(): PersonEntity {
    return PersonEntity(
        name = this.name ?: "-",
        height = this.height ?: "-",
        mass = this.mass ?: "-",
        hairColor = this.hairColor?.capitalize() ?: "-",
        skinColor = this.skinColor?.capitalize() ?: "-",
        eyeColor = this.eyeColor?.capitalize() ?: "-",
        birthYear = this.birthYear ?: "-",
        gender = this.gender?.capitalize() ?: "-",
        homeworld = this.homeworld?.capitalize() ?: "-",
        species = ArrayList(this.species?.map { it.replace("\\D".toRegex(), "") } ?: listOf())
    )
}

fun PersonEntity.toUI(): PersonUI {
    return PersonUI(
        this.name,
        this.height,
        this.mass,
        this.hairColor,
        this.skinColor,
        this.eyeColor,
        this.birthYear,
        this.gender,
        this.homeworld,
        this.isFavorite,
        this.species,
        this.tryAgainPosition
    )
}

fun PersonUI.toEntity(): PersonEntity {
    return PersonEntity(
        this.name,
        this.height,
        this.mass,
        this.hairColor,
        this.skinColor,
        this.eyeColor,
        this.birthYear,
        this.gender,
        this.homeworld,
        this.isFavorite,
        this.species ?: arrayListOf(),
        this.tryAgainPosition
    )
}

fun ResponsePeople.toConfigEntity(): ConfigEntity {
    return ConfigEntity(
        count = this.count ?: 10
    )
}

fun Planet.toUI(): PlanetUI {
    return PlanetUI(
        name = this.name?.capitalize() ?: "N/a"
    )
}

fun Species.toUI(): SpeciesUI {
    return SpeciesUI(
        name = this.name?.capitalize() ?: "N/a"
    )
}

