package com.arthurgonzaga.wikistarwars.repository

import android.util.Log
import com.arthurgonzaga.wikistarwars.api.services.FavoriteService
import com.arthurgonzaga.wikistarwars.api.services.PeopleService
import com.arthurgonzaga.wikistarwars.data.dao.CharacterDAO
import com.arthurgonzaga.wikistarwars.repository.interfaces.MainRepository
import com.arthurgonzaga.wikistarwars.viewmodel.HomeViewModel
import retrofit2.HttpException
import retrofit2.await
import retrofit2.awaitResponse
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val favoriteService: FavoriteService,
    private val dao: CharacterDAO
): MainRepository {

    override suspend fun favoriteCharacter(characterId: Int): Boolean {
        return try {
            val isSuccessful =  favoriteService.setFavorite(characterId).awaitResponse().isSuccessful
            dao.favorite(characterId, isSuccessful)
            isSuccessful
        }catch (e: HttpException){
            false
        }
    }

    override suspend fun unFavoriteCharacter(characterId: Int) {
        dao.unFavorite(characterId)
    }

    override suspend fun synchronizeFavoriteCharacters(): Boolean {
        TODO("Not yet implemented")
    }

}