package com.github.weslleystos.shared.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "peoples")
data class People(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val name: String,

    val height: String,

    val mass: String,

    @ColumnInfo(name = "hair_color")
    @SerializedName("hair_color")
    val hairColor: String,

    @ColumnInfo(name = "skin_color")
    @SerializedName("skin_color")
    val skinColor: String,

    @ColumnInfo(name = "eye_color")
    @SerializedName("eye_color")
    val eyeColor: String,

    @ColumnInfo(name = "birth_year")
    @SerializedName("birth_year")
    val birthYear: String,

    val gender: String,

    @ColumnInfo(name = "homeworld")
    @SerializedName("homeworld")
    val homeWorld: String,

    var specie: String?,
) {
    @Ignore
    val species: List<String>? = null
}