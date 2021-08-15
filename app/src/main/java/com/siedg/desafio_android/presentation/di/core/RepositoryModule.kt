package com.siedg.desafio_android.presentation.di.core

import com.siedg.desafio_android.data.repository.PersonRepositoryImpl
import com.siedg.desafio_android.data.repository.datasource.PersonCacheDataSource
import com.siedg.desafio_android.data.repository.datasource.PersonRemoteDataSource
import com.siedg.desafio_android.domain.PersonRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: PersonRemoteDataSource,
        cacheDataSource: PersonCacheDataSource
    ) : PersonRepository{
        return PersonRepositoryImpl(remoteDataSource, cacheDataSource)
    }
}