package com.github.weslleystos

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.github.weslleystos.shared.services.DatabaseService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class App : Application() {
    companion object {
        lateinit var getContext: Context
        lateinit var database: DatabaseService
    }

    override fun onCreate() {
        super.onCreate()
        getContext = applicationContext

        database = Room.databaseBuilder(
            applicationContext,
            DatabaseService::class.java,
            "database.db"
        ).build()

        GlobalScope.launch(Dispatchers.IO) {
            database.clearAllTables()
        }
    }
}