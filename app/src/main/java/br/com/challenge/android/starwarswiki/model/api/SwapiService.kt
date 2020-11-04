package br.com.challenge.android.starwarswiki.model.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SwapiService {

    @GET("/people/")
    fun getAllPeople(): Observable<PeopleReturned>

    @GET("/people/")
    fun getPersonByName(
        @Query("search")
        name: String): Observable<PeopleReturned>
}