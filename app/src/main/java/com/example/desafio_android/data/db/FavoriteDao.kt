package com.example.desafio_android.data.db

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface FavoriteDao {

    @Insert
    suspend fun addToFavorite(people: FavoritePeople)

}