package com.arthurgonzaga.wikistarwars.api

import com.arthurgonzaga.wikistarwars.api.responses.PageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PeopleService {

    @GET("/people/")
    fun getPeoplePage(
        @Query("page") page: Int
    ): Call<PageResponse>

}