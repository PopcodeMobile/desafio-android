package com.example.starwarswiki.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//@Database(entities = [DatabasePerson::class], version = 1, exportSchema = false)
//abstract class PersonRoomDatabase: RoomDatabase(){
//    abstract val personDao: PersonDao
//}
//
//private lateinit var INSTANCE: PersonRoomDatabase
//
//fun getDatabase(context: Context): PersonRoomDatabase{
//    synchronized(PersonRoomDatabase::class.java){
//        if (!::INSTANCE.isInitialized){
//            INSTANCE = Room.databaseBuilder(
//                context.applicationContext,
//                PersonRoomDatabase::class.java,
//                "persons"
//            ).build()
//        }
//    }
//    return INSTANCE
//}