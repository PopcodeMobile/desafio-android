package br.com.example.starwars.presentation.di

import android.content.Context
import br.com.example.starwars.data.local.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = AppDataBase.build(context)

    @Provides
    @Singleton
    fun providePeopleDao(appDataBase: AppDataBase) = appDataBase.peopleDao()

    @Provides
    @Singleton
    fun provideRemoteKeysDao(appDataBase: AppDataBase) = appDataBase.remoteKeysDao()
}