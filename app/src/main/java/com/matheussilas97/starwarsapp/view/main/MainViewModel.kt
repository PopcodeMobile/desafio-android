package com.matheussilas97.starwarsapp.view.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matheussilas97.starwarsapp.R
import com.matheussilas97.starwarsapp.api.Apifactory
import com.matheussilas97.starwarsapp.api.response.CharactersListResponse
import com.matheussilas97.starwarsapp.api.service.ApiServices
import com.matheussilas97.starwarsapp.utils.PaginationScrollListener
import com.matheussilas97.starwarsapp.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val services = Apifactory.create(ApiServices::class.java)

    private var _lastPage: Long = 9
    private var _currentPage: Long = 1
    val listCharacters = MutableLiveData<CharactersListResponse>()

    fun getListCharacter() {
        services.listCharacters(_currentPage).enqueue(object : Callback<CharactersListResponse> {
            override fun onResponse(
                call: Call<CharactersListResponse>,
                response: Response<CharactersListResponse>
            ) {
                if (response.isSuccessful) {
                    _currentPage++
                    listCharacters.value = response.body()
                } else {
                    val a = ""
                }
            }

            override fun onFailure(call: Call<CharactersListResponse>, t: Throwable) {
                val a = ""
            }

        })
    }

    fun isLastPage(): Boolean {
        return _currentPage < _lastPage
    }

    fun clearCurrentPage() {
        this._currentPage = PaginationScrollListener.PAGE_START.toLong()
    }
}