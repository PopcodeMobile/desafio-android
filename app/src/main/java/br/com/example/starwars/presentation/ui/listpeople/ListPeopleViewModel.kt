package br.com.example.starwars.presentation.ui.listpeople

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import br.com.example.starwars.domain.entities.People
import br.com.example.starwars.domain.usecase.FavoritePerson
import br.com.example.starwars.domain.usecase.GetPeopleList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPeopleViewModel @Inject constructor(
    private val getPeopleList: GetPeopleList,
    private val favoritePerson: FavoritePerson
) : ViewModel() {

    @ExperimentalPagingApi
    suspend fun getList(): Flow<PagingData<People>> {
        return getPeopleList.execute().cachedIn(viewModelScope)
    }

    internal fun favoritePerson(people: People) {
        viewModelScope.launch {
            people.id?.let { favoritePerson.execute(people.favorite, it) }
        }
    }

    @ExperimentalPagingApi
    internal suspend fun listFavorites(): Flow<PagingData<People>> {
        return getPeopleList.execute().map { paging ->
            paging.filter {
                it.favorite
            }
        }.cachedIn(viewModelScope)
    }
}