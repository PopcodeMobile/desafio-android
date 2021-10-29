package com.matheussilas97.starwarsapp.database.dao

import androidx.room.*
import com.matheussilas97.starwarsapp.database.model.FavoriteModel

@Dao
interface FavoriteDAO {

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): List<FavoriteModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(item: FavoriteModel): Long

    @Delete
    fun deleteFavorite(item: FavoriteModel)

    @Query("SELECT * FROM favorites WHERE id = :id")
    fun load(id: String): FavoriteModel?

    @Query("SELECT * FROM favorites WHERE url = :url")
    fun isFavorite(url: String): Boolean
}