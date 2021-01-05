package com.github.weslleystos.shared.services

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.weslleystos.features.list.repository.local.IPeopleListDao
import com.github.weslleystos.shared.entities.People

@Database(entities = [People::class], version = 1, exportSchema = false)
abstract class DatabaseService : RoomDatabase() {
    abstract fun peoplesDao(): IPeopleListDao
}