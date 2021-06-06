package com.knowledge.wikisw_luan.di

import com.knowledge.wikisw_luan.data.SwAPI
import com.knowledge.wikisw_luan.data.SwCloud
import com.knowledge.wikisw_luan.data.repository.SwRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

object Modules {
    val swModule = module {
        single {
            Retrofit.Builder()
                .baseUrl("https://swapi.dev/api/")
                .client(OkHttpClient.Builder().build())
                .build()
                .create(SwAPI::class.java)
        }
        single { SwCloud(get()) }
        single { SwRepository(get()) }
    }
}