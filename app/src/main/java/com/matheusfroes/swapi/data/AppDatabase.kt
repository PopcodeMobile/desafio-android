package com.matheusfroes.swapi.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.data.model.Planet
import com.matheusfroes.swapi.data.model.Specie


@Database(entities = [Person::class, Specie::class, Planet::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
}