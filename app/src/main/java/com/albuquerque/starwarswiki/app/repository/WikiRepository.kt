package com.albuquerque.starwarswiki.app.repository

import androidx.lifecycle.LiveData
import com.albuquerque.starwarswiki.app.model.dto.ResponseFavorite
import com.albuquerque.starwarswiki.app.model.entity.ConfigEntity
import com.albuquerque.starwarswiki.app.model.entity.PersonEntity
import com.albuquerque.starwarswiki.app.model.mapper.toConfigEntity
import com.albuquerque.starwarswiki.app.model.mapper.toEntity
import com.albuquerque.starwarswiki.app.model.mapper.toUI
import com.albuquerque.starwarswiki.app.model.ui.PersonUI
import com.albuquerque.starwarswiki.core.network.WikiResult
import com.albuquerque.starwarswiki.core.repository.WikiRepository

class WikiRepository(
    private val local: IWikiLocalDataSource,
    private val remote: IWikiRemoteDataSource
) : WikiRepository(), IWikiRepository {

    override suspend fun getPeople(shouldClearTable: Boolean, page: Int): WikiResult<List<PersonEntity>> {
        return when (val result = remote.getPeople(page)) {

            is WikiResult.Success -> {

                val peopleFromDB = local.getPeopleSuspend()
                val peopleEntity = result.data.results?.map { it.toEntity() } ?: listOf()

                peopleFromDB.forEach { db ->
                    peopleEntity.forEach { person ->

                        if (person.name == db.name)
                            person.isFavorite = db.isFavorite

                    }
                }

                local.saveConfig(result.data.toConfigEntity())
                local.savePeoples(peopleEntity, shouldClearTable)
                WikiResult.Success(peopleEntity)
            }

            is WikiResult.Failure -> {
                WikiResult.Failure(result.error)
            }
        }
    }

    override suspend fun search(search: String): WikiResult<List<PersonUI>> {
        return when (val result = remote.search(search)) {

            is WikiResult.Success -> {
                val peopleFromDB = local.getPeopleSuspend()
                val peopleEntity = result.data.results?.map { it.toEntity() } ?: listOf()

                peopleFromDB.forEach { db ->
                    peopleEntity.forEach { person ->

                        if (person.name == db.name)
                            person.isFavorite = db.isFavorite

                    }
                }

                WikiResult.Success(peopleEntity.map { it.toUI() })
            }

            is WikiResult.Failure -> {
                WikiResult.Failure(result.error)
            }
        }
    }

    override fun getPeopleFromDB(isFavorite: Boolean): LiveData<List<PersonEntity>> =
        local.getPeople(isFavorite)

    override fun getTryAgainFromDB(): LiveData<MutableList<PersonEntity>> = local.getTryAgainPeople()

    override suspend fun updatePerson(person: PersonUI) {
        local.updatePerson(person.toEntity())
    }

    override suspend fun favorite(): WikiResult<ResponseFavorite> {
        return remote.favorite()
    }

    override fun getConfig(): LiveData<ConfigEntity?> = local.getConfig()
}