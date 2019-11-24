package com.example.starwarswiki.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface PersonService{
    @GET("people")
    fun getPersonList():Call<String>
}

object PersonNetworkService{
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://swapi.co/api/")
        .addConverterFactory(ScalarsConverterFactory.create())
//        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val personList =retrofit.create(PersonService::class.java)
}