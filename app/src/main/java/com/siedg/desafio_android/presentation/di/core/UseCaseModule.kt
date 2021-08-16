package com.siedg.desafio_android.presentation.di.core

import com.siedg.desafio_android.domain.*
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {
    @Provides
    fun provideGetPersonListUseCase(repository: PersonRepository): GetPersonListUseCase {
        return GetPersonListUseCase(repository)
    }

    @Provides
    fun provideGetPlanetListUseCase(repository: PlanetRepository): GetPlanetListUseCase {
        return GetPlanetListUseCase(repository)
    }

    @Provides
    fun provideGetSpecieListUseCase(repository: SpecieRepository): GetSpecieListUseCase {
        return GetSpecieListUseCase(repository)
    }
}