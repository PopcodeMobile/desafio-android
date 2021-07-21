package com.example.starwarsapi.ui.starWarsInfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.starwarsapi.model.api.Starwars
import com.example.starwarsapi.service.StarWarsApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StarWarsInfoViewModel : ViewModel() {
    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("https://swapi.dev/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: StarWarsApiService = retrofit.create(StarWarsApiService::class.java)

    val starWarsInfo = MutableLiveData<Starwars>()

    fun getStarWarsInfo(id: Int){
        val call = service.getStarWarsInfo(id)

        call.enqueue(object : Callback<Starwars>{
            override fun onResponse(call: Call<Starwars>, response: Response<Starwars>) {
                response.body()?.let { starwars ->
                    starWarsInfo.postValue(starwars)
                }
            }

            override fun onFailure(call: Call<Starwars>, t: Throwable) {
                call.cancel()

            }

        })
    }
}