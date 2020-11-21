package br.com.example.starwars.presentation.ui.listpeople

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.example.starwars.domain.entities.People
import br.com.example.starwars.domain.usecase.FavoritePerson
import br.com.example.starwars.domain.usecase.GetPeopleList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ListPeopleViewModel @ViewModelInject constructor(
    private val getPeopleList: GetPeopleList,
    private val favoritePerson: FavoritePerson
) : ViewModel() {

    suspend fun getList(): Flow<PagingData<People>> {
        return getPeopleList.execute().cachedIn(viewModelScope)
    }

    internal fun favoritePerson(people: People) {
        viewModelScope.launch {
            people.id?.let { favoritePerson.execute(people.favorite, it) }
        }
    }

    internal suspend fun listFavorites(): Flow<PagingData<People>> {
        return getPeopleList.execute().map { paging ->
            paging.filterSync {
                it.favorite
            }
        }.cachedIn(viewModelScope)
    }
}