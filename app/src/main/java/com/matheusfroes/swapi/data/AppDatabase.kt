package com.matheusfroes.swapi.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.matheusfroes.swapi.data.dao.PersonDAO
import com.matheusfroes.swapi.data.model.Person


@Database(entities = [Person::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDAO(): PersonDAO
}