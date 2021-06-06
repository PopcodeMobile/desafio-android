package com.knowledge.wikisw_luan.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knowledge.wikisw_luan.data.repository.SwRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: SwRepository) : ViewModel() {
    fun getCharacters() {
        viewModelScope.launch {
            val list = repository.getCharacters()
            println(list)
        }
    }
}