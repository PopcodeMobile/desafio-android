package com.matheusfroes.swapi.data.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "people")
data class Person(
        @PrimaryKey
        val id: Long,
        val name: String,
        val height: String,
        val mass: String,
        val hairColor: String,
        val skinColor: String,
        val eyeColor: String,
        val birthYear: String,
        val gender: String,
        @Embedded(prefix = "planet_")
        val planet: Planet? = null,
        val isBookmarked: Boolean) {


    @Ignore
    var species: List<Specie> = listOf()
}