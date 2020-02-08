package com.albuquerque.starwarswiki.repository

import com.albuquerque.starwarswiki.model.dto.ResponsePeople
import retrofit2.http.GET

interface WikiAPI {

    @GET("people/")
    suspend fun fetchPeople(): ResponsePeople

}