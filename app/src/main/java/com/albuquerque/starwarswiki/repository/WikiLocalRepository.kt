package com.albuquerque.starwarswiki.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import com.albuquerque.starwarswiki.core.repository.WikiDataSource
import com.albuquerque.starwarswiki.dao.WikiDAO
import com.albuquerque.starwarswiki.model.entity.PeopleEntity

class WikiLocalRepository(
    private val wikiDao: WikiDAO
): WikiDataSource(), IWikiLocalDataSource {

    override fun getPeople(): LiveData<List<PeopleEntity>> {
        return wikiDao.getPeople().distinctUntilChanged()
    }

    override suspend fun savePeoples(peoples: List<PeopleEntity>, shouldClearTable: Boolean) {
        wikiDao.saveAllTransaction(peoples, shouldClearTable)
    }
}