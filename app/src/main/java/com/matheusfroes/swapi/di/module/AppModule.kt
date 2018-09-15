package com.matheusfroes.swapi.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Context) {

    @Provides
    @Singleton
    fun app(): Context = app
}