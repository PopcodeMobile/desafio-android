package com.siedg.desafio_android.presentation.di.core

import com.siedg.desafio_android.data.repository.datasource.PersonCacheDataSource
import com.siedg.desafio_android.data.repository.datasourceImpl.PersonCacheDataSourceImpl
import com.siedg.desafio_android.data.repository.datasourceImpl.PersonRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheDataModule {
    @Singleton
    @Provides
    fun providePersonCacheDataSource(): PersonCacheDataSource {
        return PersonCacheDataSourceImpl()
    }
}