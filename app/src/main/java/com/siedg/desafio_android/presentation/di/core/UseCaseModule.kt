package com.siedg.desafio_android.presentation.di.core

import com.siedg.desafio_android.domain.GetPersonListUseCase
import com.siedg.desafio_android.domain.PersonRepository
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {
    @Provides
    fun provideGetPersonListUseCase(repository: PersonRepository): GetPersonListUseCase {
        return GetPersonListUseCase(repository)
    }
}