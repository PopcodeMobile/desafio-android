package com.arthurgonzaga.wikistarwars.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.arthurgonzaga.wikistarwars.repository.interfaces.HomeRepository
import com.arthurgonzaga.wikistarwars.repository.interfaces.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: HomeRepository,
    val mainRepository: MainRepository
) : ViewModel(), LifecycleObserver {


    private val _currentSearchText = MutableLiveData("")

    val characters = _currentSearchText.switchMap { query ->
        if(query.isBlank()){
            repository.getCharacters("").cachedIn(viewModelScope)
        }else {
            repository.getCharacters(query).cachedIn(viewModelScope)
        }
    }

    private val _newFavorite = MutableLiveData<Boolean?>()
    val newFavorite: LiveData<Boolean?> = _newFavorite


    fun favoriteCharacter(characterId: Int, favorite: Boolean){
        viewModelScope.launch {
            if(favorite){
                _newFavorite.value = mainRepository.favoriteCharacter(characterId)
                _newFavorite.value = null
            }else {
                mainRepository.unFavoriteCharacter(characterId)
            }
        }
    }

    fun search(text: String) {
        _currentSearchText.value = text
    }

    companion object {
        const val TAG = "HomeViewModel"
    }
}