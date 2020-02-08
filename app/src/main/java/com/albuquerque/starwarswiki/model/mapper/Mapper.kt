package com.albuquerque.starwarswiki.model.mapper

import com.albuquerque.starwarswiki.model.dto.People
import com.albuquerque.starwarswiki.model.entity.PeopleEntity
import com.albuquerque.starwarswiki.model.ui.PeopleUI

fun People.toEntity(): PeopleEntity {
    return PeopleEntity(
        name = this.name ?: "-",
        height = this.height ?: "-",
        mass = this.mass ?: "-",
        hairColor = this.hairColor ?: "-",
        skinColor = this.skinColor ?: "-",
        eyeColor = this.eyeColor ?: "-",
        birthYear = this.birthYear ?: "-",
        gender = this.gender ?: "-",
        homeworld = this.homeworld ?: "-"
    )
}

fun PeopleEntity.toUI(): PeopleUI {
    return PeopleUI(
        this.name,
        this.height,
        this.mass,
        this.hairColor,
        this.skinColor,
        this.eyeColor,
        this.birthYear,
        this.gender,
        this.homeworld
    )
}