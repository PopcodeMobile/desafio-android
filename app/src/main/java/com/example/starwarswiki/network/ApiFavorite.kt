package com.example.starwarswiki.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteService{
    @POST("favorite/{id}")
    fun postFavorite(
        @Header("Prefer") code: Int = 201,
        @Path("id") id: Int
    ): Deferred<Response<FavoriteNetworkObject>>
}

object FavoriteRetrofit{
    val retrofit = Retrofit.Builder()
        .baseUrl("https://private-anon-c97e67cfcb-starwarsfavorites.apiary-mock.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(FavoriteService::class.java)
}