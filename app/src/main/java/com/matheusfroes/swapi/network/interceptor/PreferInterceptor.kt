package com.matheusfroes.swapi.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.util.*
import javax.inject.Inject

class PreferInterceptor @Inject constructor(
        private val randomNumberGenerator: Random
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val host = request.url().host()

        if (host.contains("starwarsfavorites", ignoreCase = true)) {
            val addPreferHeader = randomNumberGenerator.nextInt(2) == 1

            if (addPreferHeader) {
                request = request.newBuilder()
                        .addHeader("Prefer", "status=400")
                        .build()
            }
        }

        return chain.proceed(request)
    }
}