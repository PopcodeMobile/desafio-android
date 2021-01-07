package com.example.starwars.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.data.room.ResultEntity
import com.example.starwars.repository.RepositoryApi
import kotlinx.coroutines.launch

class PeopleViewModel(private val repository: RepositoryApi) : ViewModel() {

    val myResponse: MutableLiveData<List<ResultEntity>> = MutableLiveData()
    var next: Boolean = false

    // Metoto Get
    fun getPeople(page: String) {
        viewModelScope.launch {
            val response = repository.getPeople(page)
            if (response.isSuccessful) {
                myResponse.value = response.body()?.results
                next = true
            } else {
                myResponse.value = null
            }
        }
    }
}