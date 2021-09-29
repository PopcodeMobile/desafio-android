package com.arthurgonzaga.wikistarwars.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.arthurgonzaga.wikistarwars.api.services.FavoriteService
import com.arthurgonzaga.wikistarwars.api.services.HomeWorldService
import com.arthurgonzaga.wikistarwars.api.services.PeopleService
import com.arthurgonzaga.wikistarwars.api.services.SpeciesService
import com.arthurgonzaga.wikistarwars.data.Constants
import com.arthurgonzaga.wikistarwars.data.WikiStarWarsDB
import com.arthurgonzaga.wikistarwars.data.dao.CharacterDAO
import com.arthurgonzaga.wikistarwars.repository.HomeRepositoryImpl
import com.arthurgonzaga.wikistarwars.repository.MainRepositoryImpl
import com.arthurgonzaga.wikistarwars.repository.interfaces.HomeRepository
import com.arthurgonzaga.wikistarwars.repository.interfaces.MainRepository
import com.arthurgonzaga.wikistarwars.util.getRetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun providePeopleService(): PeopleService = getRetrofitInstance()

    @Provides
    @Singleton
    fun provideFavoriteService(): FavoriteService =
        getRetrofitInstance(Constants.FAVORITE_API_BASE_URL)

    @Provides
    @Singleton
    fun provideSpeciesService(): SpeciesService = getRetrofitInstance()

    @Provides
    @Singleton
    fun provideHomeWorldService(): HomeWorldService = getRetrofitInstance()

    @Provides
    @Singleton
    fun provideRoomDatabase(
        application: Application
    ): WikiStarWarsDB = Room.databaseBuilder(
            application,
            WikiStarWarsDB::class.java,
            WikiStarWarsDB.NAME
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideCharacterDAO(
        database: WikiStarWarsDB
    ): CharacterDAO = database.charactersDAO()


    @Provides
    @Singleton
    fun provideMainRepository(
        favoriteService: FavoriteService,
        dao: CharacterDAO
    ): MainRepository = MainRepositoryImpl(favoriteService, dao)

}