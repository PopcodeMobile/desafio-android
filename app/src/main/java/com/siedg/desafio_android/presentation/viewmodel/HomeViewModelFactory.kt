package com.siedg.desafio_android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.siedg.desafio_android.domain.GetPersonListUseCase
import java.lang.IllegalArgumentException

class HomeViewModelFactory(
    private val getPersonListUseCase: GetPersonListUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(getPersonListUseCase) as T
        }
        throw IllegalArgumentException("Argument invalid for View Model")
    }

}