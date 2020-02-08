package com.albuquerque.starwarswiki.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.albuquerque.starwarswiki.app.model.ui.PersonUI
import com.albuquerque.starwarswiki.app.usecase.FavoriteUseCase
import com.albuquerque.starwarswiki.app.usecase.GetPeopleUseCase
import com.albuquerque.starwarswiki.app.view.handler.PersonHandler
import com.albuquerque.starwarswiki.core.mediator.SingleMediatorLiveData
import com.albuquerque.starwarswiki.core.network.WikiException
import com.albuquerque.starwarswiki.core.network.WikiResult
import com.albuquerque.starwarswiki.core.viewmodel.WikiViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PeopleViewModel(
    private val getPeopleUseCase: GetPeopleUseCase,
    private val favoriteUseCase: FavoriteUseCase
): WikiViewModel(), PersonHandler {

    val people = SingleMediatorLiveData<List<PersonUI>>()
    val onHandleFavorite = MutableLiveData<Pair<Int?, String>>()

    init {
        getPeoples()
    }

    private fun getPeoples() {
        getPeopleUseCase(false).onEach {

            people.emit(it)

        }.catch { error ->

            if(people.value != null && people.value!!.isEmpty())
                return@catch

            if (error is WikiException) {
                onError.value = error.errorMessage

            }

        }.launchIn(viewModelScope)
    }

    override fun handleFavorite(person: PersonUI, position: Int) {

        viewModelScope.launch {
            val result = favoriteUseCase.invoke(person)

            when (result) {

                is WikiResult.Success -> {
                    onHandleFavorite.value = Pair(position, result.data!!)
                }

                is WikiResult.Failure -> {
                    onHandleFavorite.value = Pair(null, result.error.message ?: "Erro")
                }

            }

        }


    }
}