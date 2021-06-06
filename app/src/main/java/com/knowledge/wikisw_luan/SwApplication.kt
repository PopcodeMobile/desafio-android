package com.knowledge.wikisw_luan

import android.app.Application
import androidx.room.Room
import com.knowledge.wikisw_luan.data.cache.CharacterDatabase
import com.knowledge.wikisw_luan.di.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SwApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val db = Room.databaseBuilder(
            applicationContext,
            CharacterDatabase::class.java, "sw-db"
        ).build()

        startKoin {
            androidLogger()
            androidContext(this@SwApplication)
            modules(arrayListOf(Modules.swModule))
        }
    }
}