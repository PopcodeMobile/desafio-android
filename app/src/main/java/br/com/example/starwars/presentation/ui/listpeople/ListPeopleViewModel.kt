package br.com.example.starwars.presentation.ui.listpeople

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.example.starwars.domain.entities.People
import br.com.example.starwars.domain.usecase.GetPeopleList
import kotlinx.coroutines.flow.Flow

class ListPeopleViewModel @ViewModelInject constructor(
    private val getPeopleList: GetPeopleList
) : ViewModel() {

    suspend fun getList(): Flow<PagingData<People>> {
        return getPeopleList.execute().cachedIn(viewModelScope)
    }
}