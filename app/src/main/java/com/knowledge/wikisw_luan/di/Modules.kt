package com.knowledge.wikisw_luan.di

import com.google.gson.GsonBuilder
import com.knowledge.wikisw_luan.activity.MainViewModel
import com.knowledge.wikisw_luan.data.SwAPI
import com.knowledge.wikisw_luan.data.SwCloud
import com.knowledge.wikisw_luan.data.repository.SwRepository
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Modules {
    val swModule = module {
        single {
            Retrofit.Builder()
                .baseUrl("https://swapi.dev/api/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .client(OkHttpClient.Builder().build())
                .build()
                .create(SwAPI::class.java)
        }
        single { SwCloud(get()) }
        single { SwRepository(get()) }
        viewModel { MainViewModel(get()) }
    }
}