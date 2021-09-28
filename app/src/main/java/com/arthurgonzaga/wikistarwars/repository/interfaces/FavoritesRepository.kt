package com.arthurgonzaga.wikistarwars.repository.interfaces

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity

interface FavoritesRepository {

    fun getFavoriteCharacters(): LiveData<PagingData<CharacterEntity>>

    suspend fun unFavorite(characterId: Int)

}