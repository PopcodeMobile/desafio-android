package com.arthurgonzaga.wikistarwars.di

import android.app.Application
import androidx.room.Room
import com.arthurgonzaga.wikistarwars.api.services.FavoriteService
import com.arthurgonzaga.wikistarwars.api.services.PeopleService
import com.arthurgonzaga.wikistarwars.data.Constants
import com.arthurgonzaga.wikistarwars.data.WikiStarWarsDB
import com.arthurgonzaga.wikistarwars.util.getRetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideRoomDatabase(
        application: Application
    ): WikiStarWarsDB = Room.databaseBuilder(
            application,
            WikiStarWarsDB::class.java,
            WikiStarWarsDB.NAME
        ).fallbackToDestructiveMigration().build()

}