package com.matheussilas97.starwarsapp.view.charactersdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matheussilas97.starwarsapp.api.Apifactory
import com.matheussilas97.starwarsapp.api.response.CharactersDetailsResponse
import com.matheussilas97.starwarsapp.api.service.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharactersDetailsViewModel:ViewModel() {

    private val services = Apifactory.create(ApiServices::class.java)

    fun getDetails(id: String): MutableLiveData<CharactersDetailsResponse>{
        val result = MutableLiveData<CharactersDetailsResponse>()

        services.details(id).enqueue(object : Callback<CharactersDetailsResponse>{
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
}