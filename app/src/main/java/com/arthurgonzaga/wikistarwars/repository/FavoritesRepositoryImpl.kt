package com.arthurgonzaga.wikistarwars.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.arthurgonzaga.wikistarwars.data.WikiStarWarsDB
import com.arthurgonzaga.wikistarwars.data.dao.CharacterDAO
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity
import com.arthurgonzaga.wikistarwars.repository.interfaces.FavoritesRepository
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    val db: WikiStarWarsDB
) : FavoritesRepository{

    override fun getFavoriteCharacters(): LiveData<PagingData<CharacterEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false)
        ){
            db.charactersDAO().getAllFavoriteCharacters()
        }.liveData
    }

    override suspend fun unFavorite(characterId: Int) {
        db.charactersDAO().unFavorite(characterId)
    }
}