package br.com.challenge.android.starwarswiki.model.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SwapiService {

    @GET("people/")
    fun getAllPeople(
        @Query("page")
        pageNumber: Int): Observable<PeopleReturned>

    @GET("people/")
    fun getPersonByName(
        @Query("search")
        name: String): Observable<PeopleReturned>
}