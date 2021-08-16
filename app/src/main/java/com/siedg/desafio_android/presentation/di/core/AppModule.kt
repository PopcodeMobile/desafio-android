package com.siedg.desafio_android.presentation.di.core

import android.content.Context
import com.siedg.desafio_android.presentation.di.home.HomeSubComponent
import dagger.Module
import dagger.Provides
import java.security.AccessControlContext
import javax.inject.Singleton

@Module(
    subcomponents = [HomeSubComponent::class]
)
class AppModule(private val context: Context) {
    @Singleton
    @Provides
    fun provideApplicationContext(): Context {
        return context.applicationContext
    }
}