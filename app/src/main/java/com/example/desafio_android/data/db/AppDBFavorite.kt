package com.example.desafio_android.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [FavoritePeople::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(TypeConverts::class)
abstract class AppDBFavorite : RoomDatabase() {

    abstract fun getFavoriteDao(): FavoriteDao

}