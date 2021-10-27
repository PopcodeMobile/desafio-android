package com.matheussilas97.starwarsapp.view.charactersdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matheussilas97.starwarsapp.api.Apifactory
import com.matheussilas97.starwarsapp.api.response.CharactersDetailsResponse
import com.matheussilas97.starwarsapp.api.response.HomeWorldResponse
import com.matheussilas97.starwarsapp.api.response.SpeciesResponse
import com.matheussilas97.starwarsapp.api.service.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharactersDetailsViewModel:ViewModel() {

    private val services = Apifactory.create(ApiServices::class.java)

    fun getDetails(url: String): MutableLiveData<CharactersDetailsResponse>{
        val result = MutableLiveData<CharactersDetailsResponse>()

        services.details(url).enqueue(object : Callback<CharactersDetailsResponse>{
            override fun onResponse(
                call: Call<CharactersDetailsResponse>,
                response: Response<CharactersDetailsResponse>
            ) {
                if (response.isSuccessful){
                    result.value = response.body()
                }else{
                    result.value = null!!
                }
            }

            override fun onFailure(call: Call<CharactersDetailsResponse>, t: Throwable) {
                result.value = null!!
            }

        })

        return result
    }

    fun getHomeWorld(url: String): MutableLiveData<HomeWorldResponse>{
        val result = MutableLiveData<HomeWorldResponse>()

        services.getHomeWorld(url).enqueue(object : Callback<HomeWorldResponse>{
            override fun onResponse(
                call: Call<HomeWorldResponse>,
                response: Response<HomeWorldResponse>
            ) {
                if (response.isSuccessful){
                 result.value = response.body()
                }
            }

            override fun onFailure(call: Call<HomeWorldResponse>, t: Throwable) {

            }

        })

        return result
    }

    fun getSpecies(url: String): MutableLiveData<SpeciesResponse>{
        val result = MutableLiveData<SpeciesResponse>()

        services.getSpecies(url).enqueue(object : Callback<SpeciesResponse>{
            override fun onResponse(
                call: Call<SpeciesResponse>,
                response: Response<SpeciesResponse>
            ) {
                if (response.isSuccessful){
                    result.value = response.body()
                }else{
                    val a = ""
                }
            }

            override fun onFailure(call: Call<SpeciesResponse>, t: Throwable) {
                val a = ""
            }

        })

        return result
    }
}