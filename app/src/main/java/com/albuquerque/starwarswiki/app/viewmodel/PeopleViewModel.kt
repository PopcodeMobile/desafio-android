package com.albuquerque.starwarswiki.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.albuquerque.starwarswiki.app.model.ui.PersonUI
import com.albuquerque.starwarswiki.app.usecase.*
import com.albuquerque.starwarswiki.app.view.handler.PersonHandler
import com.albuquerque.starwarswiki.core.livedata.SingleLiveEvent
import com.albuquerque.starwarswiki.core.mediator.SingleMediatorLiveData
import com.albuquerque.starwarswiki.core.network.StringWrapper
import com.albuquerque.starwarswiki.core.network.WikiResult
import com.albuquerque.starwarswiki.core.viewmodel.WikiViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.math.ceil

class PeopleViewModel(
    private val getPeopleUseCase: GetPeopleUseCase,
    private val favoriteUseCase: FavoriteUseCase,
    private val getSearchUseCase: GetSearchUseCase,
    private val setTryAgainUseCase: SetTryAgainUseCase,
    private val getTryAgainUseCase: GetTryAgainUseCase,
    private val getConfigUseCase: GetConfigUseCase
) : WikiViewModel(), PersonHandler {

    var people: SingleMediatorLiveData<List<PersonUI>> = SingleMediatorLiveData()

    var tryFavoriteAgainList = getTryAgainUseCase.invoke()
    var isTryAgainEnabled = true

    val onHandleFavorite = MutableLiveData<Pair<Int?, String>>()
    val onHandleClick = MutableLiveData<PersonUI>()

    val onStartLoading = SingleLiveEvent<Void>()
    val onFinishLoading = SingleLiveEvent<Void>()

    var config = getConfigUseCase.invoke()

    private var count = PAGINATION_FIRST_PAGE

    private var lastPage = PAGINATION_FIRST_PAGE

    private var pagination = lastPage

    init {
        getPeople()
    }

    fun getPeople() {
        onStartLoading.call()

        getPeopleUseCase(
            pagination == PAGINATION_FIRST_PAGE,
            pagination
        ).onEach {
            people.emit(it)
            pagination = pagination.plus(1)
            onFinishLoading.call()

        }.catch { error ->
            onError.value = StringWrapper(error.message ?: "Erro ao carregar personagens!")
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
        if (pagination > count) return
        getPeople()
    }

    fun clearPeople() {
        people.value = listOf()
    }

    fun clearTryAgainList() {
        tryFavoriteAgainList = MutableLiveData()
    }

    fun setCount(value: Int) {
        count = ceil(value.toDouble().div(PAGINATION_SIZE)).toInt()
    }

    fun setLastPage(size: Int) {
        lastPage = ceil(size.toDouble().div(PAGINATION_SIZE)).toInt()
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
                    onHandleFavorite.value =
                        Pair(null, result.error.message ?: "Erro ao favoritar.")
                    onFinishLoading.call()
                }

            }

        }

    }

    override fun handleClick(person: PersonUI) {
        onHandleClick.value = person
    }

}