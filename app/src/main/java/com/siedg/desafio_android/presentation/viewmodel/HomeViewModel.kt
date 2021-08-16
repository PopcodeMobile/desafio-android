package com.siedg.desafio_android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.siedg.desafio_android.domain.GetPersonListUseCase
import com.siedg.desafio_android.domain.GetPlanetListUseCase
import com.siedg.desafio_android.domain.GetSpecieListUseCase

class HomeViewModel (
    private val getPersonListUseCase: GetPersonListUseCase,
    private val getPlanetListUseCase: GetPlanetListUseCase,
    private val getSpecieListUseCase: GetSpecieListUseCase
): ViewModel() {
    fun getPersonList() = liveData {
        val personList = getPersonListUseCase.execute()
        emit(personList)
    }

    fun getPlanetList() = liveData {
        val planetList = getPlanetListUseCase.execute()
        emit(planetList)
    }

    fun getSpecieList() = liveData {
        val specieList = getSpecieListUseCase.execute()
        emit(specieList)
    }
}