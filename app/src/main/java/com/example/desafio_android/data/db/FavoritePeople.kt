package com.example.desafio_android.data.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.desafio_android.data.model.People
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favorite_people")
@Parcelize
data class FavoritePeople(
    var people: People,
    var name: String
): Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}