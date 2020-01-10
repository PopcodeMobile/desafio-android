package br.com.starwarswiki.services

import br.com.starwarswiki.models.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SWServiceApi : BaseApi() {

    override val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://swapi.co/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(SWService::class.java)

    fun getPeople(callback: (people: ServerResponse<Person>?, error: String?) -> Unit) {
        service.getPeople().enqueue(handleResponse(callback))
    }

    fun getPlanets(callback: (planets: ServerResponse<Planet>?, error: String?) -> Unit) {
        service.getPlanets().enqueue(handleResponse(callback))
    }

    fun getSpecies(callback: (species: ServerResponse<Specie>?, error: String?) -> Unit) {
        service.getSpecies().enqueue(handleResponse(callback))
    }
}