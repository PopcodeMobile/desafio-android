package br.albuquerque.domain.repository

import br.albuquerque.data.dto.Species
import br.albuquerque.data.dto.Planet
import br.albuquerque.data.dto.ResponseFavorite
import br.albuquerque.data.dto.ResponsePeople
import retrofit2.http.*

interface WikiAPI {

    @GET("people/")
    suspend fun fetchPeople(@Query("page") page: Int): ResponsePeople

    @POST("favorite/abc123")
    suspend fun favorite(
        @Header("Prefer") value: String = ""
    ): ResponseFavorite

    @GET("people/")
    suspend fun search(
        @Query("search") value: String
    ): ResponsePeople

    @GET("planets/{planetID}")
    suspend fun fetchHomePlanet(
        @Path("planetID") id: String
    ): Planet

    @GET("species/{speciesID}")
    suspend fun fetchSpecies(
        @Path("speciesID") id: String
    ): Species

}