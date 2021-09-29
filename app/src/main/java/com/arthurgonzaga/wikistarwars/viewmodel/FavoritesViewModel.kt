package com.arthurgonzaga.wikistarwars.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.arthurgonzaga.wikistarwars.repository.interfaces.FavoritesRepository
import com.arthurgonzaga.wikistarwars.repository.interfaces.HomeRepository
import com.arthurgonzaga.wikistarwars.repository.interfaces.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoritesViewModel @Inject constructor(
    favoritesRepository: FavoritesRepository,
    private val mainRepository: MainRepository
): ViewModel() {

    val favoritesCharacters = favoritesRepository.getFavoriteCharacters().cachedIn(viewModelScope)

    fun unFavoriteCharacter(id: Int){
        viewModelScope.launch {
            mainRepository.unFavoriteCharacter(id)
        }
    }
}