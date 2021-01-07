package com.github.weslleystos.features.list.repository.remote

import com.github.weslleystos.shared.entities.People
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IPeopleListRepository {
    @GET("people/")
    fun getAll(@Query("page") page: Int): Call<Response>
}

data class Response(
    val count: Int,
    val next: Any,
    val previous: String,
    val results: List<People>
)