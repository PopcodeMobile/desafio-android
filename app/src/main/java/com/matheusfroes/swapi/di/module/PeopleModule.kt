package com.matheusfroes.swapi.di.module

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.matheusfroes.swapi.BuildConfig
import com.matheusfroes.swapi.di.People
import com.matheusfroes.swapi.network.PeopleService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class PeopleModule {

    @Provides
    @People
    fun retrofit(
            okHttpClient: OkHttpClient,
            coroutineCallAdapterFactory: CoroutineCallAdapterFactory,
            gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(coroutineCallAdapterFactory)
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(BuildConfig.SWAPI_BASE_URL)
            .build()

    @Provides
    fun peopleService(@People retrofit: Retrofit): PeopleService {
        return retrofit.create(PeopleService::class.java)
    }
}
