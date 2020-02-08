package com.albuquerque.starwarswiki.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import com.albuquerque.starwarswiki.core.repository.WikiDataSource
import com.albuquerque.starwarswiki.app.dao.WikiDAO
import com.albuquerque.starwarswiki.app.model.entity.PersonEntity

class WikiLocalRepository(
    private val wikiDao: WikiDAO
): WikiDataSource(), IWikiLocalDataSource {

    override fun getPeople(): LiveData<List<PersonEntity>> {
        return wikiDao.getPeople().distinctUntilChanged()
    }

    override suspend fun savePeoples(people: List<PersonEntity>, shouldClearTable: Boolean) {
        wikiDao.saveAllTransaction(people, shouldClearTable)
    }

    override suspend fun updatePerson(person: PersonEntity) {
        wikiDao.updatePerson(person)
    }

    override suspend fun getPeopleSuspend(): List<PersonEntity> {
        return wikiDao.getPeopleSuspend()
    }
}