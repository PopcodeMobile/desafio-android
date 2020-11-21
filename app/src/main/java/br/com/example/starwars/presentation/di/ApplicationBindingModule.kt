package br.com.example.starwars.presentation.di

import br.com.example.starwars.data.repository.FavoriteRepositoryImpl
import br.com.example.starwars.data.repository.GetPeopleListRepositoryImpl
import br.com.example.starwars.data.repository.GetPlanetRepositoryImpl
import br.com.example.starwars.data.repository.GetSpecieRepositoryImpl
import br.com.example.starwars.domain.repository.FavoriteRepository
import br.com.example.starwars.domain.repository.GetPeopleListRepository
import br.com.example.starwars.domain.repository.GetPlanetRepository
import br.com.example.starwars.domain.repository.GetSpecieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
interface ApplicationBindingModule {

    @Binds
    fun bindGetPeopleListRepository(
        repository: GetPeopleListRepositoryImpl
    ): GetPeopleListRepository

    @Binds
    fun bindGetPlanetRepository(
        repository: GetPlanetRepositoryImpl
    ): GetPlanetRepository

    @Binds
    fun bindGetSpecieRepository(
        repository: GetSpecieRepositoryImpl
    ): GetSpecieRepository

    @Binds
    fun bindFavoriteRepository(
        repository: FavoriteRepositoryImpl
    ): FavoriteRepository
}