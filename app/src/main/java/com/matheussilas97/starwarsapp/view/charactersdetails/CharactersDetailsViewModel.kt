package com.matheussilas97.starwarsapp.view.charactersdetails

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.matheussilas97.starwarsapp.R
import com.matheussilas97.starwarsapp.api.Apifactory
import com.matheussilas97.starwarsapp.api.favorites.ApiFactoryFavorites
import com.matheussilas97.starwarsapp.api.favorites.FavoriteErrorResponse
import com.matheussilas97.starwarsapp.api.favorites.FavoritesResponse
import com.matheussilas97.starwarsapp.api.favorites.FavoritesService
import com.matheussilas97.starwarsapp.api.response.CharactersDetailsResponse
import com.matheussilas97.starwarsapp.api.response.HomeWorldResponse
import com.matheussilas97.starwarsapp.api.response.SpeciesResponse
import com.matheussilas97.starwarsapp.api.service.ApiServices
import com.matheussilas97.starwarsapp.database.model.FavoriteModel
import com.matheussilas97.starwarsapp.database.repository.FavoriteRepository
import com.matheussilas97.starwarsapp.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharactersDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val services = Apifactory.create(ApiServices::class.java)
    private val serviceFavorite = ApiFactoryFavorites.create(FavoritesService::class.java)
    private val favoriteRepository = FavoriteRepository(application.applicationContext)

    private var mSaveStatus = MutableLiveData<Boolean>()
    val saveStatus: LiveData<Boolean> = mSaveStatus

    private var mSaveFavorite = MutableLiveData<Boolean>()
    val saveFavorite: LiveData<Boolean> = mSaveFavorite

    fun postFavorite(id: String): MutableLiveData<String> {
        val result = MutableLiveData<String>()

        serviceFavorite.postFavorites(id).enqueue(object : Callback<FavoritesResponse> {
            override fun onResponse(
                call: Call<FavoritesResponse>,
                response: Response<FavoritesResponse>
            ) {
                if (response.isSuccessful) {
                    result.value = response.body()?.message
                    mSaveStatus.value = true
                } else {
                    val errorMessage =
                        Gson().fromJson(
                            response.errorBody()?.string(),
                            FavoriteErrorResponse::class.java
                        )
                    val error = errorMessage.errorMessage
                    result.value = error
                    mSaveStatus.value = false
                }
            }

            override fun onFailure(call: Call<FavoritesResponse>, t: Throwable) {
                mSaveStatus.value = true
            }

        })

        return result
    }

    fun getDetails(url: String, context: Context): MutableLiveData<CharactersDetailsResponse> {
        val result = MutableLiveData<CharactersDetailsResponse>()
        val dialog = Utils.showLoading(context, R.string.loading)
        services.details(url).enqueue(object : Callback<CharactersDetailsResponse> {
            override fun onResponse(
                call: Call<CharactersDetailsResponse>,
                response: Response<CharactersDetailsResponse>
            ) {
                if (response.isSuccessful) {
                    result.value = response.body()
                    dialog.dismiss()
                } else {
                    result.value = null!!
                    dialog.dismiss()
                }
            }

            override fun onFailure(call: Call<CharactersDetailsResponse>, t: Throwable) {
                result.value = null!!
                dialog.dismiss()
            }

        })

        return result
    }

    fun saveClass(favorite: FavoriteModel) {
//        if (favorite.id == 0) {
            mSaveFavorite.value = favoriteRepository.addStudents(favorite)
//        }
    }

    fun getHomeWorld(url: String): MutableLiveData<HomeWorldResponse> {
        val result = MutableLiveData<HomeWorldResponse>()

        services.getHomeWorld(url).enqueue(object : Callback<HomeWorldResponse> {
            override fun onResponse(
                call: Call<HomeWorldResponse>,
                response: Response<HomeWorldResponse>
            ) {
                if (response.isSuccessful) {
                    result.value = response.body()
                }
            }

            override fun onFailure(call: Call<HomeWorldResponse>, t: Throwable) {

            }

        })

        return result
    }

    fun getSpecies(url: String): MutableLiveData<SpeciesResponse> {
        val result = MutableLiveData<SpeciesResponse>()

        services.getSpecies(url).enqueue(object : Callback<SpeciesResponse> {
            override fun onResponse(
                call: Call<SpeciesResponse>,
                response: Response<SpeciesResponse>
            ) {
                if (response.isSuccessful) {
                    result.value = response.body()
                } else {
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