package com.albuquerque.starwarswiki.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.albuquerque.starwarswiki.app.model.ui.PersonUI
import com.albuquerque.starwarswiki.app.usecase.FavoriteUseCase
import com.albuquerque.starwarswiki.app.usecase.GetSpeciesUseCase
import com.albuquerque.starwarswiki.app.usecase.GetHomePlanetUseCase
import com.albuquerque.starwarswiki.app.usecase.SetTryAgainUseCase
import com.albuquerque.starwarswiki.app.view.handler.PersonHandler
import com.albuquerque.starwarswiki.core.livedata.SingleLiveEvent
import com.albuquerque.starwarswiki.core.network.WikiResult
import com.albuquerque.starwarswiki.core.viewmodel.WikiViewModel
import kotlinx.coroutines.launch

class PersonDetailViewModel(
    private val getHomePlanetUseCase: GetHomePlanetUseCase,
    private val getSpeciesUseCase: GetSpeciesUseCase,
    private val favoriteUseCase: FavoriteUseCase,
    private val setTryAgainUseCase: SetTryAgainUseCase
) : WikiViewModel(), PersonHandler {

    private lateinit var person: PersonUI

    val onHandleFavorite = MutableLiveData<Pair<Int?, String>>()

    val homePlanet = MutableLiveData<String>().apply { value = "N/a" }
    val species = MutableLiveData<String>().apply { value = "N/a" }

    val onStartLoading = SingleLiveEvent<Void>()
    val onFinishLoading = SingleLiveEvent<Void>()

    fun setPerson(person: PersonUI) {
        this.person = person
    }

    fun fetchData() {

        viewModelScope.launch {

            val resultPlanet =
                getHomePlanetUseCase.invoke(person.homeworld.takeLast(2).replace("/", ""))
            when (resultPlanet) {
                is WikiResult.Success -> homePlanet.value = resultPlanet.data.name
                is WikiResult.Failure -> onError.value = resultPlanet.error.errorMessage
            }

            val speciesList = mutableListOf<String>()

            person.species?.forEach { species ->
                val resultSpecies = getSpeciesUseCase.invoke(species)

                when (resultSpecies) {
                    is WikiResult.Success -> speciesList.add(resultSpecies.data.name)
                    is WikiResult.Failure -> onError.value = resultSpecies.error.errorMessage
                }
            }

            species.value = speciesList.joinToString(",")

        }

    }

    override fun handleFavorite(person: PersonUI, position: Int) {
        viewModelScope.launch {
            onStartLoading.call()
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

    override fun handleClick(person: PersonUI) {}
}