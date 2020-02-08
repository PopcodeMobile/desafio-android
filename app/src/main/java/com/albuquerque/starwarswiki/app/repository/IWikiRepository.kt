package com.albuquerque.starwarswiki.app.repository

import androidx.lifecycle.LiveData
import com.albuquerque.starwarswiki.app.model.dto.ResponseFavorite
import com.albuquerque.starwarswiki.app.model.entity.PersonEntity
import com.albuquerque.starwarswiki.app.model.ui.PersonUI
import com.albuquerque.starwarswiki.core.network.WikiResult

interface IWikiRepository {

    suspend fun getPeople(shouldClearTable: Boolean): WikiResult<List<PersonEntity>>
    fun getPeopleFromDB():LiveData<List<PersonEntity>>
    suspend fun updatePerson(person: PersonUI)
    suspend fun favorite(): WikiResult<ResponseFavorite>

}