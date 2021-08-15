package com.siedg.desafio_android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.siedg.desafio_android.domain.GetPersonListUseCase

class HomeViewModel (
    private val getPersonListUseCase: GetPersonListUseCase
): ViewModel() {
    fun getPersonList() = liveData {
        val personList = getPersonListUseCase.execute()
        emit(personList)
    }
}