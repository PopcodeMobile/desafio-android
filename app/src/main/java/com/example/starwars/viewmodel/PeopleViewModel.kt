package com.example.starwars.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.data.room.ResultEntity
import com.example.starwars.repository.RepositoryApi
import kotlinx.coroutines.launch

class PeopleViewModel ( private val repository: RepositoryApi): ViewModel() {

    val myResponse: MutableLiveData<List<ResultEntity>> = MutableLiveData()

    // Metoto Get
    fun getPeople(){
        viewModelScope.launch {
            val response = repository.getPeople()
            if (response.isSuccessful) {
                myResponse.value = response.body()?.results
            }else{
                myResponse.value = null
            }
        }
    }
}