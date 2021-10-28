package com.matheussilas97.starwarsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.matheussilas97.starwarsapp.database.dao.FavoriteDAO
import com.matheussilas97.starwarsapp.database.model.FavoriteModel

@Database(entities = [FavoriteModel::class], version = 1)
abstract class MainDataBase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDAO

    companion object {
        private lateinit var INSTANCE: MainDataBase
        fun getDatabase(context: Context): MainDataBase {
            if (!::INSTANCE.isInitialized) {
                synchronized(MainDataBase::class.java) {
                    INSTANCE = Room.databaseBuilder(context, MainDataBase::class.java, "favDB")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}