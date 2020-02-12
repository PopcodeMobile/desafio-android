package com.albuquerque.starwarswiki.app.model.mapper

import com.albuquerque.starwarswiki.app.model.dto.Person
import com.albuquerque.starwarswiki.app.model.dto.ResponsePeople
import com.albuquerque.starwarswiki.app.model.entity.ConfigEntity
import com.albuquerque.starwarswiki.app.model.entity.PersonEntity
import com.albuquerque.starwarswiki.app.model.ui.PersonUI

fun Person.toEntity(): PersonEntity {
    return PersonEntity(
        name = this.name ?: "-",
        height = this.height ?: "-",
        mass = this.mass ?: "-",
        hairColor = this.hairColor ?: "-",
        skinColor = this.skinColor ?: "-",
        eyeColor = this.eyeColor ?: "-",
        birthYear = this.birthYear ?: "-",
        gender = this.gender?.capitalize() ?: "-",
        homeworld = this.homeworld ?: "-"
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
        this.tryAgainPosition
    )
}

fun ResponsePeople.toConfigEntity(): ConfigEntity {
    return ConfigEntity(
        count = this.count ?: 10
    )
}

