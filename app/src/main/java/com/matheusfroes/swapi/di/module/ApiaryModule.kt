package com.matheusfroes.swapi.di.module

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.matheusfroes.swapi.BuildConfig
import com.matheusfroes.swapi.di.Apiary
import com.matheusfroes.swapi.network.ApiaryService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiaryModule {

    @Provides
    @Apiary
    fun retrofit(
            okHttpClient: OkHttpClient,
            coroutineCallAdapterFactory: CoroutineCallAdapterFactory,
            gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(coroutineCallAdapterFactory)
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(BuildConfig.APIARY_BASE_URL)
            .build()

    @Provides
    fun peopleService(@Apiary retrofit: Retrofit): ApiaryService {
        return retrofit.create(ApiaryService::class.java)
    }
}
