package com.albuquerque.starwarswiki.viewmodel

import androidx.lifecycle.viewModelScope
import com.albuquerque.starwarswiki.core.mediator.SingleMediatorLiveData
import com.albuquerque.starwarswiki.core.network.WikiException
import com.albuquerque.starwarswiki.core.viewmodel.WikiViewModel
import com.albuquerque.starwarswiki.model.ui.PeopleUI
import com.albuquerque.starwarswiki.usecase.GetPeopleUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PeopleViewModel(
    private val getPeopleUseCase: GetPeopleUseCase
): WikiViewModel() {

    val peoples = SingleMediatorLiveData<List<PeopleUI>>()

    init {
        getPeoples()
    }

    private fun getPeoples() {
        getPeopleUseCase(false).onEach {

            peoples.emit(it)

        }.catch { error ->

            if(peoples.value != null && peoples.value!!.isEmpty())
                return@catch

            if (error is WikiException) {
                onError.value = error.errorMessage

            }

        }.launchIn(viewModelScope)
    }

}