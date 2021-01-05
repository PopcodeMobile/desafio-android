package com.example.starwars.data.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize

//Criação da tabela
@Entity(tableName = "result_table")
//Atributos da tabela
data class ResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val height: String,
    val gender: String,
    val mass: String,
    val hair_color: String,
    val skin_color: String,
    val eye_color: String,
    val birth_year: String,
    val homeworld: String
): Parcelable
