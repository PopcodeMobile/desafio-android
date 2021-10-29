package com.matheussilas97.starwarsapp.database.repository

import android.content.Context
import com.matheussilas97.starwarsapp.database.MainDataBase
import com.matheussilas97.starwarsapp.database.model.FavoriteModel

class FavoriteRepository(context: Context) {

    private val mDataBase = MainDataBase.getDatabase(context).favoriteDao()


    fun listFavorites(): List<FavoriteModel> = mDataBase.getAllFavorites()

    fun addStudents(favorite: FavoriteModel): Boolean = mDataBase.insertFavorite(favorite) > 0

    fun deleteStudents(favorite: FavoriteModel) = mDataBase.deleteFavorite(favorite)

    fun getLoad(id: String): FavoriteModel? = mDataBase.load(id)

    fun isFavorite(url: String): Boolean = mDataBase.isFavorite(url)

}