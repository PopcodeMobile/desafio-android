package br.com.example.starwars.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "star-wars-db"

        fun build(context: Context): AppDataBase {
            return Room.databaseBuilder(
                context,
                AppDataBase::class.java, DATABASE_NAME
            ).build()
        }
    }
}