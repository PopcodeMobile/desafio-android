package com.matheussilas97.starwarsapp.view.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.matheussilas97.starwarsapp.api.favorites.ApiFactoryFavorites
import com.matheussilas97.starwarsapp.api.favorites.FavoriteErrorResponse
import com.matheussilas97.starwarsapp.api.favorites.FavoritesResponse
import com.matheussilas97.starwarsapp.api.favorites.FavoritesService
import com.matheussilas97.starwarsapp.database.model.FavoriteModel
import com.matheussilas97.starwarsapp.database.repository.FavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteViewModel (application: Application) : AndroidViewModel(application) {

   private val service = ApiFactoryFavorites.create(FavoritesService::class.java)
    private val favoriteRepository = FavoriteRepository(application.applicationContext)

    private val mFavoriteList = MutableLiveData<List<FavoriteModel>>()
    val favoriteList: LiveData<List<FavoriteModel>> = mFavoriteList

    fun list() {
        mFavoriteList.value = favoriteRepository.listFavorites()
    }

    fun deleteFavorite(id: String) {
        val favorite = favoriteRepository.getLoad(id)
        if (favorite != null) {
            favoriteRepository.deleteStudents(favorite)
        }
    }


}