package com.example.starwarsapi.ui.starWarsList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.starwarsapi.model.api.StarWarsApiResponse
import com.example.starwarsapi.model.api.StarWarsResult
import com.example.starwarsapi.service.StarWarsApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StarWarsListViewModel(): ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://swapi.dev/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service: StarWarsApiService = retrofit.create(StarWarsApiService::class.java)

    val starWarsList = MutableLiveData<List<StarWarsResult>>()

    fun getStarWarsList(){
        val call = service.getStarWarsList(100,0)

        call.enqueue(object  : Callback<StarWarsApiResponse>{
            override fun onResponse(call: Call<StarWarsApiResponse>, response: Response<StarWarsApiResponse>) {
                response.body()?.results?.let{list ->
                    starWarsList.postValue(list)
                }
            }

            override fun onFailure(call: Call<StarWarsApiResponse>, t: Throwable) {
                call.cancel()
            }

        })
    }
}