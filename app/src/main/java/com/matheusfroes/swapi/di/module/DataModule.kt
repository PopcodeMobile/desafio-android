package com.matheusfroes.swapi.di.module

import android.arch.persistence.room.Room
import android.content.Context
import com.matheusfroes.swapi.data.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun database(context: Context): AppDatabase {
        return Room
                .databaseBuilder(context, AppDatabase::class.java, "swapi.db")
                .build()
    }
}