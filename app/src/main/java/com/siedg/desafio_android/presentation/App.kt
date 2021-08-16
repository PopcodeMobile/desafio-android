package com.siedg.desafio_android.presentation

import android.app.Application
import com.siedg.desafio_android.BuildConfig
import com.siedg.desafio_android.presentation.di.Injector
import com.siedg.desafio_android.presentation.di.core.*
import com.siedg.desafio_android.presentation.di.home.HomeSubComponent

class App : Application(), Injector {
    private lateinit var appComponent: AppComponent

    override fun createHomeSubComponent(): HomeSubComponent = appComponent.homeSubComponent().create()

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .networkModule(NetworkModule(BuildConfig.BASE_URL))
            .remoteDataModule(RemoteDataModule())
            .build()
    }
}