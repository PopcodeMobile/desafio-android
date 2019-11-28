package com.example.starwarswiki.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.starwarswiki.util.Converters

@Database(entities = [DatabasePerson::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PersonRoomDatabase: RoomDatabase(){
    abstract val personDao: PersonDao
    companion object{
        private lateinit var INSTANCE: PersonRoomDatabase

        fun getDatabase(context: Context): PersonRoomDatabase{
            synchronized(PersonRoomDatabase::class.java){
                if (!::INSTANCE.isInitialized){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        PersonRoomDatabase::class.java,
                        "people"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}
