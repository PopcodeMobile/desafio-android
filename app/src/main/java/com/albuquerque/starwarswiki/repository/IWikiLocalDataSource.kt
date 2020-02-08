package com.albuquerque.starwarswiki.repository

import androidx.lifecycle.LiveData
import com.albuquerque.starwarswiki.model.entity.PeopleEntity

interface IWikiLocalDataSource {

    fun getPeople(): LiveData<List<PeopleEntity>>
    suspend fun savePeoples(peoples: List<PeopleEntity>, shouldClearTable: Boolean)

}