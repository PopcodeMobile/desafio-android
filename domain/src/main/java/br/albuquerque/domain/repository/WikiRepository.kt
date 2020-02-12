package br.albuquerque.domain.repository

import androidx.lifecycle.LiveData
import br.albuquerque.core.network.WikiResult
import br.albuquerque.data.dto.ResponseFavorite
import br.albuquerque.data.entity.ConfigEntity
import br.albuquerque.data.entity.PersonEntity
import br.albuquerque.data.util.toEntity
import br.albuquerque.data.ui.SpeciesUI
import br.albuquerque.data.ui.PersonUI
import br.albuquerque.data.ui.PlanetUI
import br.albuquerque.core.repository.WikiRepository
import br.albuquerque.data.util.toConfigEntity
import br.albuquerque.data.util.toUI

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

    override suspend fun getPlanetHome(id: String): WikiResult<PlanetUI> {
        return when (val result = remote.getHomePlanet(id)) {

            is WikiResult.Success -> {

                WikiResult.Success(result.data.toUI())
            }

            is WikiResult.Failure -> {
                WikiResult.Failure(result.error)
            }
        }
    }

    override suspend fun getSpecies(id: String): WikiResult<SpeciesUI> {

        return when (val result = remote.getSpecies(id)) {

            is WikiResult.Success -> {

                WikiResult.Success(result.data.toUI())
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