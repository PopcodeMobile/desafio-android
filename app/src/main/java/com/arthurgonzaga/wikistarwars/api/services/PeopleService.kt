package com.arthurgonzaga.wikistarwars.api.services

import com.arthurgonzaga.wikistarwars.api.responses.PageResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PeopleService {

    @GET("people")
    fun getPeoplePage(
        @Query("page") page: Int,
        @Query("search") query: String = ""
    ): Single<PageResponse>

}