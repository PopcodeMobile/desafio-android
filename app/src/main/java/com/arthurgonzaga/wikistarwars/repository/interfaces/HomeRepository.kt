package com.arthurgonzaga.wikistarwars.repository.interfaces

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getCharacters(): LiveData<PagingData<CharacterEntity>>
}