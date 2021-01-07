package com.example.starwars.data.api

import com.example.starwars.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(MyInterceptor())
    }.build()

    // Captura o link da Api
    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    // Inicializa a Api
    val api: SimpleApi by lazy {
        retrofit.create(SimpleApi::class.java)
    }
}