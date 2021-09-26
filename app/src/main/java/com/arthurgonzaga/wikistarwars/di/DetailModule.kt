package com.arthurgonzaga.wikistarwars.di

import com.arthurgonzaga.wikistarwars.api.services.HomeWorldService
import com.arthurgonzaga.wikistarwars.api.services.SpeciesService
import com.arthurgonzaga.wikistarwars.repository.interfaces.DetailRepository
import com.arthurgonzaga.wikistarwars.util.getRetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DetailModule {

    @Provides
    @ViewModelScoped
    fun provideSpeciesService(): SpeciesService = getRetrofitInstance()

    @Provides
    @ViewModelScoped
    fun provideHomeWorldService(): HomeWorldService = getRetrofitInstance()

    @Provides
    @ViewModelScoped
    fun provideDetailRepository(): DetailRepository = TODO()
}