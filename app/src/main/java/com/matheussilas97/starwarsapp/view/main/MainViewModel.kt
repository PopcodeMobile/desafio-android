package com.matheussilas97.starwarsapp.view.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matheussilas97.starwarsapp.R
import com.matheussilas97.starwarsapp.api.Apifactory
import com.matheussilas97.starwarsapp.api.response.CharactersListResponse
import com.matheussilas97.starwarsapp.api.service.ApiServices
import com.matheussilas97.starwarsapp.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val services = Apifactory.create(ApiServices::class.java)

    fun listCharacters(page: Int, context: Context): MutableLiveData<CharactersListResponse> {
        val result = MutableLiveData<CharactersListResponse>()

        val dialog = Utils.showLoading(context, R.string.loading)
        services.listCharacters(page).enqueue(object : Callback<CharactersListResponse> {
            override fun onResponse(
                call: Call<CharactersListResponse>,
                response: Response<CharactersListResponse>
            ) {
                if (response.isSuccessful) {
                    result.value = response.body()
                    dialog.dismiss()
                } else {
                    result.value = null!!
                    dialog.dismiss()
                }
            }

            override fun onFailure(call: Call<CharactersListResponse>, t: Throwable) {
                result.value = null!!
                dialog.dismiss()
            }

        })

        return result
    }
}