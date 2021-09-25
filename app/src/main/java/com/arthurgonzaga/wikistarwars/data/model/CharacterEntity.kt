package com.arthurgonzaga.wikistarwars.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String = "",
    val height: String = "",
    val weight: String = "",
    @ColumnInfo(name = "hair_color")
    val hairColor: String = "",
    @ColumnInfo(name = "skin_color")
    val skinColor: String = "",
    @ColumnInfo(name = "eye_color")
    val eyeColor: String = "",
    @ColumnInfo(name = "birth_year")
    val birthYear: String = "",
    val gender: String = "",
    @ColumnInfo(name = "specie_name")
    val specieName: String = "",
    @ColumnInfo(name = "home_world_name")
    val homeWoldName: String = "",
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,
    @ColumnInfo(name = "is_synchronized_with_backend")
    val isSynchronizedWithBackend: Boolean = false,
)
