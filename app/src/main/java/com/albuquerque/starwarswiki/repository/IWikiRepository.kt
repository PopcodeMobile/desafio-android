package com.albuquerque.starwarswiki.repository

import androidx.lifecycle.LiveData
import com.albuquerque.starwarswiki.core.network.WikiResult
import com.albuquerque.starwarswiki.model.entity.PeopleEntity

interface IWikiRepository {

    suspend fun getPeople(shouldClearTable: Boolean): WikiResult<List<PeopleEntity>>
    fun getPeopleFromDB():LiveData<List<PeopleEntity>>

}