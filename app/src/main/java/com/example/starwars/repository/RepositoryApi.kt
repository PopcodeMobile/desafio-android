package com.example.starwars.repository

import com.example.starwars.data.api.RetrofitInstance
import com.example.starwars.model.People
import retrofit2.Response

class RepositoryApi {

    // Metodo que chama o metodo da Api
    // Captura todos os personagens da pagina
    suspend fun getPeople(page: String): Response<People> {
        return RetrofitInstance.api.getPeople(page)
    }

}