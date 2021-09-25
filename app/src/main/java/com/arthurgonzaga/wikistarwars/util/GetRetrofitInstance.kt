package com.arthurgonzaga.wikistarwars.util

import com.arthurgonzaga.wikistarwars.data.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

inline fun <reified T> getRetrofitInstance(): T{
    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(T::class.java)
}