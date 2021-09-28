package com.arthurgonzaga.wikistarwars.api.services

import com.arthurgonzaga.wikistarwars.api.responses.HomeWorldResponse
import com.arthurgonzaga.wikistarwars.api.responses.SpecieResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeWorldService {

    @GET("planets/{id}")
    fun getHomeWorld(
        @Path("id") id: Int
    ): Single<HomeWorldResponse>
}