package com.albuquerque.starwarswiki.app.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "people")
class PersonEntity(
    @PrimaryKey @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "height") var height: String,
    @ColumnInfo(name = "mass") var mass: String,
    @ColumnInfo(name = "hairColor") var hairColor: String,
    @ColumnInfo(name = "skinColor") var skinColor: String,
    @ColumnInfo(name = "eyeColor") var eyeColor: String,
    @ColumnInfo(name = "birthYear") var birthYear: String,
    @ColumnInfo(name = "gender") var gender: String,
    @ColumnInfo(name = "homeworld") var homeworld: String,
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = false,
    @ColumnInfo(name = "tryAgainPosition") var tryAgainPosition: Int? = null
)