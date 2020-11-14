package br.com.example.starwars.presentation.di

import br.com.example.starwars.BuildConfig.API_ENDPOINT
import br.com.example.starwars.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object ApiProviderModule {

    @Singleton
    @Provides
    @Named(API_ENDPOINT)
    fun providesApiEndPoint(): String {
        return API_ENDPOINT
    }

    @Singleton
    @Provides
    fun providesRetrofit(
        @Named(API_ENDPOINT) apiEndPoint: String,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(apiEndPoint)
            .build()
    }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}