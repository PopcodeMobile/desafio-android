package com.siedg.desafio_android.presentation.di.core

import com.siedg.desafio_android.data.api.ApiService
import com.siedg.desafio_android.data.repository.datasource.PersonRemoteDataSource
import com.siedg.desafio_android.data.repository.datasourceImpl.PersonRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataModule {
    @Singleton
    @Provides
    fun providePersonRemoteDataSource(apiService: ApiService): PersonRemoteDataSource {
        return PersonRemoteDataSourceImpl(apiService)
    }
}