package br.com.challenge.android.starwarswiki.model.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiRetrofitService {
    companion object {
        private const val SWAPI_BASE_URL = "https://swapi.dev/api/"
        @Volatile
        private var INSTANCE: Retrofit? = null

        fun getInstance(): Retrofit {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Retrofit.Builder()
                        .baseUrl(SWAPI_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(getOkHttpClientBuilder().build())
                        .build()

                    INSTANCE = instance
                }

                return instance!!
            }

        }

        private fun getOkHttpClientBuilder(): OkHttpClient.Builder {
            val okHttpBuilder = OkHttpClient.Builder()

            okHttpBuilder.addInterceptor {chain ->
                val request = chain.request().newBuilder()
                val originalHttpUrl = chain.request().url()

                request.url(originalHttpUrl)

                return@addInterceptor chain.proceed(request.build())
            }

            return okHttpBuilder
        }
    }
}