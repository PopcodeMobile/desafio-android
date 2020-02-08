package com.albuquerque.starwarswiki.repository

import androidx.lifecycle.LiveData
import com.albuquerque.starwarswiki.core.network.WikiResult
import com.albuquerque.starwarswiki.core.repository.WikiRepository
import com.albuquerque.starwarswiki.model.entity.PeopleEntity
import com.albuquerque.starwarswiki.model.mapper.toEntity

class WikiRepository(
    private val local: IWikiLocalDataSource,
    private val remote: IWikiRemoteDataSource
): WikiRepository(), IWikiRepository {

    override suspend fun getPeople(shouldClearTable: Boolean): WikiResult<List<PeopleEntity>> {
        return when(val result = remote.getPeople()) {

            is WikiResult.Success -> {
                val peopleEntity = result.data.results?.map { it.toEntity() } ?: listOf()
                local.savePeoples(peopleEntity, shouldClearTable)
                WikiResult.Success(peopleEntity)
            }

            is WikiResult.Failure -> {
                WikiResult.Failure(result.error)
            }
        }
    }

    override fun getPeopleFromDB(): LiveData<List<PeopleEntity>> = local.getPeople()
}