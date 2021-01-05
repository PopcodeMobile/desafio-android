package com.example.starwars.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.data.room.ResultDatabase
import com.example.starwars.data.room.ResultEntity
import com.example.starwars.model.Results
import com.example.starwars.repository.RepositoryApi
import com.example.starwars.repository.RepositoryRoom
import kotlinx.coroutines.launch

class PeopleViewModel ( private val repository: RepositoryApi): ViewModel() {

    val myResponse: MutableLiveData<List<Results>> = MutableLiveData()

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