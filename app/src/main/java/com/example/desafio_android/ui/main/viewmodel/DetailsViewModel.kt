package com.example.desafio_android.ui.main.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.desafio_android.data.model.Planet
import com.example.desafio_android.data.model.Specie
import com.example.desafio_android.data.repository.Repository
import com.example.desafio_android.util.Resultado

class DetailsViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    suspend fun getNamePlanet(url: String): LiveData<Resultado<Planet?>> =
        repository.getNamePlanet(url)

    suspend fun getNameSpecie(url: String): LiveData<Resultado<Specie?>> =
        repository.getNameSpecie(url)

}