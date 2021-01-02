package com.example.starwars.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.model.People
import com.example.starwars.repository.RepositoryApi
import kotlinx.coroutines.launch
import retrofit2.Response

class PeopleViewModel ( private val repository: RepositoryApi): ViewModel() {

    val myResponse: MutableLiveData<Response<People>> = MutableLiveData()

    // Metoto Get
    fun getPeople(){
        viewModelScope.launch {
            val response = repository.getPeople()
            myResponse.value = response
        }
    }

}