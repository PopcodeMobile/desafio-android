package com.albuquerque.starwarswiki.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.albuquerque.starwarswiki.app.model.ui.PersonUI
import com.albuquerque.starwarswiki.app.usecase.*
import com.albuquerque.starwarswiki.app.view.handler.PersonHandler
import com.albuquerque.starwarswiki.core.livedata.SingleLiveEvent
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
    private val getSearchUseCase: GetSearchUseCase,
    private val setTryAgainUseCase: SetTryAgainUseCase,
    private val getTryAgainUseCase: GetTryAgainUseCase
) : WikiViewModel(), PersonHandler {

    var people: SingleMediatorLiveData<List<PersonUI>> = SingleMediatorLiveData()

    var tryFavoriteAgainList = getTryAgainUseCase.invoke()
    var isTryAgainEnabled = true

    val onHandleFavorite = MutableLiveData<Pair<Int?, String>>()
    val onHandleClick = MutableLiveData<PersonUI>()

    val onStartLoading = SingleLiveEvent<Void>()
    val onFinishLoading = SingleLiveEvent<Void>()

    val pagination = MutableLiveData<Int>()
    private var stopPagination = false

    init {
        getPeople()
        pagination.value = PAGINATION_FIRST_PAGE
    }

    fun getPeople() {
        onStartLoading.call()

        getPeopleUseCase(
            pagination.value == PAGINATION_FIRST_PAGE,
            pagination.value ?: 1
        ).onEach {
            people.emit(it)
            pagination.value = pagination.value?.plus(1) ?: 1
            onFinishLoading.call()

        }.catch { error ->
            (error as WikiException).code?.let { code ->
                if (code == 404) stopPagination = true
            }
            onFinishLoading.call()

        }.launchIn(viewModelScope)
    }

    fun search(search: String) {
        onStartLoading.call()

        viewModelScope.launch {
            val result = getSearchUseCase.invoke(search)

            when (result) {

                is WikiResult.Success -> {
                    people.value = result.data
                    onFinishLoading.call()
                }

                is WikiResult.Failure -> {
                    onError.value = result.error.errorMessage
                    onFinishLoading.call()
                }

            }

        }

    }

    fun handleNextPage() {
        if (stopPagination) return
        getPeople()
    }

    fun clearPeople() {
        people.value = listOf()
    }

    fun clearTryAgainList(){
        tryFavoriteAgainList = MutableLiveData()
    }

    override fun handleFavorite(person: PersonUI, position: Int) {
        onStartLoading.call()

        viewModelScope.launch {
            val result = favoriteUseCase.invoke(person)

            when (result) {

                is WikiResult.Success -> {
                    onHandleFavorite.value = Pair(position, result.data!!)
                    onFinishLoading.call()
                }

                is WikiResult.Failure -> {
                    person.tryAgainPosition = position
                    setTryAgainUseCase.invoke(person)
                    onHandleFavorite.value = Pair(null, result.error.message ?: "Erro ao favoritar.")
                    onFinishLoading.call()
                }

            }

        }

    }

    override fun handleClick(person: PersonUI) {
        onHandleClick.value = person
    }

}