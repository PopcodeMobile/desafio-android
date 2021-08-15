package com.siedg.desafio_android.presentation.di.core

import android.accounts.NetworkErrorException
import com.siedg.desafio_android.presentation.di.home.HomeSubComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        UseCaseModule::class,
        RepositoryModule::class,
        RemoteDataModule::class,
        AppModule::class,
        CacheDataModule::class,
        NetworkModule::class
    ]
)

interface AppComponent {
    fun homeSubComponent(): HomeSubComponent.Factory
}