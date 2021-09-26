package com.arthurgonzaga.wikistarwars.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import androidx.room.RoomDatabase
import com.arthurgonzaga.wikistarwars.api.services.PeopleService
import com.arthurgonzaga.wikistarwars.data.WikiStarWarsDB
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity
import com.arthurgonzaga.wikistarwars.repository.interfaces.HomeRepository
import com.arthurgonzaga.wikistarwars.repository.paging.CharacterRemoteMediator
import javax.inject.Inject


@ExperimentalPagingApi
class HomeRepositoryImpl @Inject constructor(
    private val service: PeopleService,
    private val database: WikiStarWarsDB
): HomeRepository{

    override fun getCharacters(): LiveData<PagingData<CharacterEntity>> {
        val pagingSourceFactory = { database.charactersDAO().getAllCharacters() }
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = pagingSourceFactory,
            initialKey = 1,
            remoteMediator = CharacterRemoteMediator(service, database)
        ).liveData
    }
}