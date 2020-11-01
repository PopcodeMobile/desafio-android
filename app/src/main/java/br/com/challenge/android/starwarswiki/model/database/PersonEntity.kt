package br.com.challenge.android.starwarswiki.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person")
data class PersonEntity(

    @PrimaryKey
    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "birth_year")
    var birthYear: String,

    @ColumnInfo(name = "eye_color")
    var eyeColor: String,

    @ColumnInfo(name = "gender")
    var gender: String,

    @ColumnInfo(name = "hair_color")
    var hairColor: String,

    @ColumnInfo(name = "height")
    var height: String,

    @ColumnInfo(name = "mass")
    var mass: String,

    @ColumnInfo(name = "skin_color")
    var skinColor: String,

    @ColumnInfo(name = "home_world")
    var homeWorld: String,

    @ColumnInfo(name = "species")
    var species: ArrayList<String>
)