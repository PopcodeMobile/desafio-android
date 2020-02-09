package com.albuquerque.starwarswiki.core.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class WikiInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
            .header("Content-Type", "application/json")
            .build()

        return chain.proceed(request)
    }
}