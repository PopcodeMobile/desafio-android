package br.albuquerque.domain.repository

import androidx.lifecycle.LiveData
import br.albuquerque.core.network.WikiResult
import br.albuquerque.data.dto.ResponseFavorite
import br.albuquerque.data.entity.ConfigEntity
import br.albuquerque.data.entity.PersonEntity
import br.albuquerque.data.ui.SpeciesUI
import br.albuquerque.data.ui.PersonUI
import br.albuquerque.data.ui.PlanetUI

interface IWikiRepository {

    suspend fun getPeople(shouldClearTable: Boolean, page: Int): WikiResult<List<PersonEntity>>
    suspend fun search(search: String): WikiResult<List<PersonUI>>
    suspend fun getPlanetHome(id: String): WikiResult<PlanetUI>
    suspend fun getSpecies(id: String): WikiResult<SpeciesUI>
    fun getPeopleFromDB(isFavorite: Boolean = false):LiveData<List<PersonEntity>>
    fun getTryAgainFromDB():LiveData<MutableList<PersonEntity>>
    suspend fun updatePerson(person: PersonUI)
    suspend fun favorite(): WikiResult<ResponseFavorite>
    fun getConfig(): LiveData<ConfigEntity?>

}