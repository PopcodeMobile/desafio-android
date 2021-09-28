package com.arthurgonzaga.wikistarwars.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.arthurgonzaga.wikistarwars.repository.interfaces.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: HomeRepository
) : ViewModel(), LifecycleObserver {


    private val _currentSearchText = MutableLiveData("")

    val characters = _currentSearchText.switchMap { query ->
        if(query.isBlank()){
            repository.getCharacters("").cachedIn(viewModelScope)
        }else {
            repository.getCharacters(query).cachedIn(viewModelScope)
        }
    }


    fun favoriteCharacter(characterId: Int, isFavorite: Boolean){
        viewModelScope.launch {
            repository.favoriteCharacter(characterId, isFavorite)
        }
    }

    fun search(text: String) {
        _currentSearchText.value = text
    }

    companion object {
        const val TAG = "HomeViewModel"
    }
}