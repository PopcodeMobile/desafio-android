package com.matheusfroes.swapi

import android.app.Application
import com.facebook.stetho.Stetho
import com.matheusfroes.swapi.di.DaggerInjector
import com.matheusfroes.swapi.di.Injector
import com.matheusfroes.swapi.di.module.AppModule

class SwapiApplication : Application() {
    lateinit var injector: Injector private set

    override fun onCreate() {
        super.onCreate()
        setupDagger()

        Stetho.initializeWithDefaults(this)
    }

    private fun setupDagger() {
        injector = DaggerInjector
                .builder()
                .appModule(AppModule(this))
                .build()
    }
}