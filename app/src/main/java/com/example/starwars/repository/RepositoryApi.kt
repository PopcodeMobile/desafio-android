package com.example.starwars.repository

import com.example.starwars.data.api.RetrofitInstance
import com.example.starwars.model.People
import com.example.starwars.model.Results
import retrofit2.Response

class RepositoryApi {

    // Metodo que chama o metodo da Api
    suspend fun getPeople(): Response<List<People>> {
        return RetrofitInstance.api.getPeople()
    }

}