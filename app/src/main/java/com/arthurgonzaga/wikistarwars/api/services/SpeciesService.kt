package com.arthurgonzaga.wikistarwars.api.services

import com.arthurgonzaga.wikistarwars.api.responses.PageResponse
import com.arthurgonzaga.wikistarwars.api.responses.SpecieResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpeciesService {

    @GET("species/{id}")
    fun getSpecie(
        @Path("id") id: Int
    ): Single<SpecieResponse>

}