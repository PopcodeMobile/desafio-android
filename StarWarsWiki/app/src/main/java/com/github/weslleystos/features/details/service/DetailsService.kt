package com.github.weslleystos.features.details.service

import androidx.lifecycle.MutableLiveData
import com.github.weslleystos.features.details.repository.remote.DetailsRepository
import com.github.weslleystos.shared.entities.Planet
import com.github.weslleystos.shared.entities.Specie
import com.github.weslleystos.shared.services.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsService {
    private val detailRepository = RetrofitService().create<DetailsRepository>()

    fun getHomeWorld(url: String, planetLiveData: MutableLiveData<Pair<Boolean, Planet?>>) {
        val service = detailRepository.getHomeWorld(url)
        service.enqueue(object : Callback<Planet> {
            override fun onResponse(call: Call<Planet>, response: Response<Planet>) {
                when {
                    response.isSuccessful -> planetLiveData.value = Pair(true, response.body())
                    else -> planetLiveData.value = Pair(false, null)
                }
            }

            override fun onFailure(call: Call<Planet>, t: Throwable) {
                planetLiveData.value = Pair(false, null)
            }
        })

    }

    fun getSpecie(url: String, specieLiveData: MutableLiveData<Pair<Boolean, Specie?>>) {
        val service = detailRepository.getSpecie(url)
        service.enqueue(object : Callback<Specie> {
            override fun onResponse(call: Call<Specie>, response: Response<Specie>) {
                when {
                    response.isSuccessful -> specieLiveData.value = Pair(true, response.body())
                    else -> specieLiveData.value = Pair(false, null)
                }
            }

            override fun onFailure(call: Call<Specie>, t: Throwable) {
                specieLiveData.value = Pair(false, null)
            }
        })
    }


}