package com.arthurgonzaga.wikistarwars.viewmodel

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


    fun unFavoriteCharacter(id: Int){
        viewModelScope.launch {
            mainRepository.unFavoriteCharacter(id)
        }
    }

    fun favoriteCharacter(id: Int){
        viewModelScope.launch {
            mainRepository.favoriteCharacter(id)
        }
    }
}