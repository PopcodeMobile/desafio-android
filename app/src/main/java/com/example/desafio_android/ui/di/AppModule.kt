package com.example.desafio_android.ui.di

import android.content.Context
import androidx.room.Room
import com.example.desafio_android.data.api.RequestApi
import com.example.desafio_android.data.db.AppDBFavorite
import com.example.desafio_android.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFavMovieDatabase(
        @ApplicationContext app:Context
    ) = Room.databaseBuilder(
        app,
        AppDBFavorite::class.java,
        "people_db"
    ).build()

    @Singleton
    @Provides
    fun provideFavPeopleDao(db: AppDBFavorite) = db.getFavoriteDao()

    //

    @Singleton
    @Provides
    fun providerHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun providerApiService(retrofit: Retrofit): RequestApi {
        return retrofit.create(RequestApi::class.java)
    }

}