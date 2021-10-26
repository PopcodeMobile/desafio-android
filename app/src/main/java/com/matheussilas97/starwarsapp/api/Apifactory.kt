package com.matheussilas97.starwarsapp.api

import com.matheussilas97.starwarsapp.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Apifactory {
    private val authInterceptor = Interceptor { chain ->

        val newUrl = chain.request().url
            .newBuilder()
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .header("Content-Type", "application/json")
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val apiClient = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .build()


    private fun retrofit(): Retrofit = Retrofit.Builder()
        .client(apiClient)
        .baseUrl(Constants.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T {
        return retrofit().create(serviceClass)
    }
}