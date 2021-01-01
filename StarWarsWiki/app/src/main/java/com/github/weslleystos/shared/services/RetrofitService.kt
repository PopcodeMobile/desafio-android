package com.github.weslleystos.shared.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    companion object {
        private const val URL = "https://swapi.dev/api/"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    inline fun <reified T> create(): T = retrofit.create(T::class.java)
}