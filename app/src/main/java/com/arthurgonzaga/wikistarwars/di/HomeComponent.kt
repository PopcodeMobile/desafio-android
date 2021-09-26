package com.arthurgonzaga.wikistarwars.di

import androidx.paging.ExperimentalPagingApi
import com.arthurgonzaga.wikistarwars.api.services.PeopleService
import com.arthurgonzaga.wikistarwars.data.WikiStarWarsDB
import com.arthurgonzaga.wikistarwars.repository.HomeRepositoryImpl
import com.arthurgonzaga.wikistarwars.repository.interfaces.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object HomeComponent {

    @ExperimentalPagingApi
    @Provides
    @ViewModelScoped
    fun provideHomeRepository(
        service: PeopleService,
        db: WikiStarWarsDB
    ): HomeRepository = HomeRepositoryImpl(service, db)
}