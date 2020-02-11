package com.albuquerque.starwarswiki.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.albuquerque.starwarswiki.app.model.ui.PersonUI
import com.albuquerque.starwarswiki.app.usecase.FavoriteUseCase
import com.albuquerque.starwarswiki.app.usecase.GetPeopleUseCase
import com.albuquerque.starwarswiki.app.usecase.GetSearchUseCase
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
    private val favoriteUseCase: FavoriteUseCase,
    private val getSearchUseCase: GetSearchUseCase
): WikiViewModel(), PersonHandler {

    var people: SingleMediatorLiveData<List<PersonUI>> = SingleMediatorLiveData()

    val onHandleFavorite = MutableLiveData<Pair<Int?, String>>()
    val onHandleClick = MutableLiveData<PersonUI>()
    val onRequestStarted = MutableLiveData<Void>()
    val onRequestFinished = MutableLiveData<Void>()

    val pagination = MutableLiveData<Int>()
    private var stopPagination = false

    init {
        getPeople()
        pagination.value = PAGINATION_FIRST_PAGE
    }

    fun getPeople() {
        onRequestStarted.value = null

        getPeopleUseCase(
            pagination.value == PAGINATION_FIRST_PAGE,
            pagination.value ?: 1
        ).onEach {
            people.emit(it)
            pagination.value = pagination.value?.plus(1) ?: 1
            onRequestFinished.value = null

        }.catch { error ->
            (error as WikiException).code?.let { code ->
                if(code == 404) stopPagination = true
            }
            onRequestFinished.value = null

        }.launchIn(viewModelScope)
    }

    fun search(search: String) {
        onRequestStarted.value = null

        viewModelScope.launch {
            val result = getSearchUseCase.invoke(search)

            when (result) {

                is WikiResult.Success -> {
                    people.value = result.data
                    onRequestFinished.value = null
                }

                is WikiResult.Failure -> {
                    onError.value = result.error.errorMessage
                    onRequestFinished.value = null
                }

            }

        }

    }

    fun handleNextPage() {
        if(stopPagination) return
        getPeople()
    }

    fun clearPeople() { people.value = listOf() }

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

    override fun handleClick(person: PersonUI) {
        onHandleClick.value = person
    }
}