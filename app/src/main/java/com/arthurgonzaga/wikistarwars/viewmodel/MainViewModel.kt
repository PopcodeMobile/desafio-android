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
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel() {

    private val _inSync = MutableLiveData<Boolean>()
    val inSync: LiveData<Boolean> = _inSync

    init {
        viewModelScope.launch {
            synchronizeWithBackend()
        }
    }

    private suspend fun synchronizeWithBackend(){
        _inSync.value = mainRepository.synchronizeFavoriteCharacters()
    }
}