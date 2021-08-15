package com.siedg.desafio_android.presentation.di.home

import com.siedg.desafio_android.domain.GetPersonListUseCase
import com.siedg.desafio_android.presentation.viewmodel.HomeViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class HomeModule {
    @Provides
    fun provideHomeViewModelFactory(
        getPersonListUseCase: GetPersonListUseCase
    ): HomeViewModelFactory {
        return HomeViewModelFactory(
            getPersonListUseCase
        )
    }
}