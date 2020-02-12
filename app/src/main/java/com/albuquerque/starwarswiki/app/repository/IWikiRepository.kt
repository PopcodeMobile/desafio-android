package com.albuquerque.starwarswiki.app.repository

import androidx.lifecycle.LiveData
import com.albuquerque.starwarswiki.app.model.dto.ResponseFavorite
import com.albuquerque.starwarswiki.app.model.entity.PersonEntity
import com.albuquerque.starwarswiki.app.model.ui.PersonUI
import com.albuquerque.starwarswiki.core.network.WikiResult

interface IWikiRepository {

    suspend fun getPeople(shouldClearTable: Boolean, page: Int): WikiResult<List<PersonEntity>>
    suspend fun search(search: String): WikiResult<List<PersonUI>>
    fun getPeopleFromDB(isFavorite: Boolean = false):LiveData<List<PersonEntity>>
    fun getTryAgainFromDB():LiveData<MutableList<PersonEntity>>
    suspend fun updatePerson(person: PersonUI)
    suspend fun favorite(): WikiResult<ResponseFavorite>

}