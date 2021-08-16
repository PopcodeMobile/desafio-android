package com.siedg.desafio_android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.siedg.desafio_android.domain.GetPersonListUseCase
import com.siedg.desafio_android.domain.GetPlanetListUseCase
import com.siedg.desafio_android.domain.GetSpecieListUseCase
import java.lang.IllegalArgumentException

class HomeViewModelFactory(
    private val getPersonListUseCase: GetPersonListUseCase,
    private val getPlanetListUseCase: GetPlanetListUseCase,
    private val getSpecieListUseCase: GetSpecieListUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                getPersonListUseCase,
                getPlanetListUseCase,
                getSpecieListUseCase
            ) as T
        }
        throw IllegalArgumentException("Argument invalid for View Model")
    }

}