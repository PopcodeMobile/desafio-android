package com.github.weslleystos.shared.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "peoples")
class People(
    @PrimaryKey(autoGenerate = true)
    var id: Int
) : Serializable {

    lateinit var name: String

    lateinit var height: String

    lateinit var mass: String

    @ColumnInfo(name = "hair_color")
    @SerializedName("hair_color")
    lateinit var hairColor: String

    @ColumnInfo(name = "skin_color")
    @SerializedName("skin_color")
    lateinit var skinColor: String

    @ColumnInfo(name = "eye_color")
    @SerializedName("eye_color")
    lateinit var eyeColor: String

    @ColumnInfo(name = "birth_year")
    @SerializedName("birth_year")
    lateinit var birthYear: String

    lateinit var gender: String

    @ColumnInfo(name = "homeworld")
    @SerializedName("homeworld")
    lateinit var homeWorld: String

    var specie: String? = null

    @Ignore
    val species: List<String>? = null
}