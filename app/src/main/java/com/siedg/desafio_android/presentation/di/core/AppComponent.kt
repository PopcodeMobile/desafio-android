package com.siedg.desafio_android.presentation.di.core

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        UseCaseModule::class,
        RepositoryModule::class,
        RemoteDataModule::class
    ]
)

interface AppComponent {
}