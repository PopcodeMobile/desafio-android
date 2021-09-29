package com.arthurgonzaga.wikistarwars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthurgonzaga.wikistarwars.repository.interfaces.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val mainRepository: MainRepository
): ViewModel() {


    private val _newFavorite = MutableLiveData<Boolean?>()
    val newFavorite: LiveData<Boolean?> = _newFavorite


    fun unFavoriteCharacter(id: Int){
        viewModelScope.launch {
            mainRepository.unFavoriteCharacter(id)
        }
    }

    fun favoriteCharacter(id: Int){
        viewModelScope.launch {
            _newFavorite.value = mainRepository.favoriteCharacter(id)
            _newFavorite.value = null
        }
    }
}