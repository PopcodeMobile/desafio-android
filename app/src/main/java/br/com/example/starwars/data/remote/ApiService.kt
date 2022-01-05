package br.com.example.starwars.data.remote

import br.com.example.starwars.data.entities.ApiListPeopleResponse
import br.com.example.starwars.data.entities.ApiPlanet
import br.com.example.starwars.data.entities.ApiSpecie
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @GET("people")
    suspend fun getListPeople(@Query("page") page: Int): ApiListPeopleResponse

    @GET
    suspend fun getPlanet(@Url url: String): ApiPlanet

    @GET
    suspend fun getSpecie(@Url url: String): ApiSpecie
}