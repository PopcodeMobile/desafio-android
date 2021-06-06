package com.knowledge.wikisw_luan.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val name: String,
    val mass: String,
    val height: String,
    val hairColor: String,
    val skinColor: String,
    val eyeColor: String,
    val birthYear: String,
    val gender: String,
    val homeworld: String,
    val species: String,
    var isFavorite: Boolean = false
) : Parcelable
