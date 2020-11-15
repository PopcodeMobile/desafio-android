package br.com.example.starwars.data.remote

import br.com.example.starwars.data.entities.ApiListPeopleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("people/")
    suspend fun getListPeople(@Query("page") page: Int): ApiListPeopleResponse
}