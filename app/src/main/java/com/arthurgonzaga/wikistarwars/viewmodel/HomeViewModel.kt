package com.arthurgonzaga.wikistarwars.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity
import com.arthurgonzaga.wikistarwars.repository.interfaces.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: HomeRepository
) : ViewModel(), LifecycleObserver {

    val characters: LiveData<PagingData<CharacterEntity>> = getCharactersPagingData()


    private fun getCharactersPagingData(): LiveData<PagingData<CharacterEntity>>{
        Log.i(TAG, "getting CharactersPagingData")
        return repository.getCharacters().cachedIn(viewModelScope)
    }


    fun favoriteCharacter(characterId: Int, isFavorite: Boolean){
        viewModelScope.launch {
            repository.favoriteCharacter(characterId, isFavorite)
        }
    }

    companion object {
        const val TAG = "HomeViewModel"
    }
}