package com.example.starwarswiki.network

import com.example.starwarswiki.domain.PersonModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonService{
    @GET("people")
    fun getObject(@Query("page") index: Int):Deferred<NetworkObject>
    @GET("planets/{index}/")
    fun getPlanet(@Path("index") index: Int):Deferred<PlanetNetworkObject>
    @GET("species/{index}/")
    fun getSpecie(@Path("index") index:Int):Deferred<SpecieNetworkObject>
}

object PersonNetworkService{
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://swapi.co/api/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val bruteRequest =retrofit.create(PersonService::class.java)
}