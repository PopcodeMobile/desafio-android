package com.matheusfroes.swapi

import android.app.Application
import com.matheusfroes.swapi.di.DaggerInjector
import com.matheusfroes.swapi.di.Injector
import com.matheusfroes.swapi.di.module.AppModule

class SwapiApplication : Application() {
    lateinit var injector: Injector private set

    override fun onCreate() {
        super.onCreate()
        setupDagger()
    }

    private fun setupDagger() {
        injector = DaggerInjector
                .builder()
                .appModule(AppModule(this))
                .build()
    }
}