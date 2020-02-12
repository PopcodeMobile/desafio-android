package com.albuquerque.starwarswiki.app.repository

import androidx.lifecycle.LiveData
import com.albuquerque.starwarswiki.app.model.entity.PersonEntity

interface IWikiLocalDataSource {

    fun getPeople(isFavorite: Boolean = false): LiveData<List<PersonEntity>>
    fun getTryAgainPeople(): LiveData<MutableList<PersonEntity>>
    suspend fun getPeopleSuspend(): List<PersonEntity>
    suspend fun savePeoples(people: List<PersonEntity>, shouldClearTable: Boolean)
    suspend fun updatePerson(person: PersonEntity)

}