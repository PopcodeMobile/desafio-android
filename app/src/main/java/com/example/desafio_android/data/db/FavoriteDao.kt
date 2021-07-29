package com.example.desafio_android.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.desafio_android.data.model.People

@Dao
interface FavoriteDao {

    @Insert
    suspend fun addToFavorite(people: FavoritePeople)

    @Query("SELECT people FROM favorite_people")
    fun getFavorites(): LiveData<List<People>>

    @Query("SELECT count(*) FROM favorite_people WHERE favorite_people.nome = :namePeople")
    suspend fun checkPeople(namePeople: String): Int

    @Query("DELETE FROM favorite_people WHERE favorite_people.people = :people")
    suspend fun removeFromFavorite(people: People)
}