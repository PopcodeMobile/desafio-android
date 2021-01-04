package com.example.starwars.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ResultEntity::class], version = 1, exportSchema = false)
abstract class ResultDatabase: RoomDatabase() {

    //Cria o Database

    abstract fun userDao(): ResultDao

    companion object{
        @Volatile
        private var INSTANCE: ResultDatabase? = null

        fun getDatabase(context: Context): ResultDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ResultDatabase::class.java,
                    "result_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}