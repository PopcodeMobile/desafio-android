package com.albuquerque.starwarswiki.app.model.mapper

import com.albuquerque.starwarswiki.app.model.dto.Species
import com.albuquerque.starwarswiki.app.model.dto.Person
import com.albuquerque.starwarswiki.app.model.dto.Planet
import com.albuquerque.starwarswiki.app.model.dto.ResponsePeople
import com.albuquerque.starwarswiki.app.model.entity.ConfigEntity
import com.albuquerque.starwarswiki.app.model.entity.PersonEntity
import com.albuquerque.starwarswiki.app.model.ui.SpeciesUI
import com.albuquerque.starwarswiki.app.model.ui.PersonUI
import com.albuquerque.starwarswiki.app.model.ui.PlanetUI

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

