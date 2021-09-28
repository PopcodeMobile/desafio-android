package com.arthurgonzaga.wikistarwars.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.arthurgonzaga.wikistarwars.repository.interfaces.FavoritesRepository
import com.arthurgonzaga.wikistarwars.repository.interfaces.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoritesViewModel @Inject constructor(
    val repository: FavoritesRepository
): ViewModel() {

    val favoritesCharacters = repository.getFavoriteCharacters().cachedIn(viewModelScope)

    fun unFavorite(id: Int){
        viewModelScope.launch {
            repository.unFavorite(id)
        }
    }
}