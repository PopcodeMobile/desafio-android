package com.example.desafio_android.data.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.desafio_android.data.model.People
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity(tableName = "favorite_people")
@Parcelize
data class FavoritePeople(
    var nome: String,
    var people: People,
) : Serializable, Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}