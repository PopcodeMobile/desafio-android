package com.matheusfroes.swapi.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.util.*
import javax.inject.Inject

/**
 * OkHttp request interceptor used to modify requests sent to the apiary API
 * Adds 'Prefer:status=400' header to roughly half of the requests
 * Uses a random number generator to generate a number between 0 and 1 and add the header accordingly
 */
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