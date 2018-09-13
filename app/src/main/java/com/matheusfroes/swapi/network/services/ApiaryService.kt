package com.matheusfroes.swapi.network.services

import com.matheusfroes.swapi.network.data.ApiarySuccessResponse
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiaryService {

    @POST("favorite/{id}")
    fun bookmarkPerson(
            @Path("id") personId: Long
    ): Deferred<Response<ApiarySuccessResponse>>
}