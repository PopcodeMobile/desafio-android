package br.com.example.starwars.presentation.di

import br.com.example.starwars.data.repository.GetPeopleListRepositoryImpl
import br.com.example.starwars.domain.repository.GetPeopleListRepository
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
}