package br.com.example.starwars.presentation.di

import android.content.Context
import br.com.example.starwars.data.local.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = AppDataBase.build(context)
}