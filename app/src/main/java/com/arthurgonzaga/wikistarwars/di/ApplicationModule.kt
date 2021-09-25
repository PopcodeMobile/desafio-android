package com.arthurgonzaga.wikistarwars.di

import com.arthurgonzaga.wikistarwars.api.FavoriteService
import com.arthurgonzaga.wikistarwars.api.PeopleService
import com.arthurgonzaga.wikistarwars.data.Constants
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
    fun provideFavoriteService(): FavoriteService = getRetrofitInstance(Constants.FAVORITE_API_BASE_URL)

}