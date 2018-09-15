package com.matheusfroes.swapi.di.module

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.matheusfroes.swapi.network.Connectivity
import com.matheusfroes.swapi.network.interceptor.PreferInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

@Module()
class NetworkModule {

    @Provides
    fun gson(): Gson = GsonBuilder().create()

    @Provides
    fun gsonConverter(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Provides
    fun coroutinesAdapter(): CoroutineCallAdapterFactory = CoroutineCallAdapterFactory()

    @Provides
    fun okHttpClient(preferInterceptor: PreferInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(preferInterceptor)
                .build()
    }

    @Provides
    fun preferInterceptor(random: Random) = PreferInterceptor(random)

    @Provides
    @Singleton
    fun random() = Random()


    @Provides
    fun connectivity(context: Context) = Connectivity(context)
}
