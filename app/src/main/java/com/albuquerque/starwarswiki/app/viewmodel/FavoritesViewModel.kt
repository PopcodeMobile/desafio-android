package com.albuquerque.starwarswiki.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.albuquerque.data.ui.PersonUI
import br.albuquerque.domain.usecase.FavoriteUseCase
import br.albuquerque.domain.usecase.GetFavoritesUseCase
import com.albuquerque.starwarswiki.app.view.handler.PersonHandler
import br.albuquerque.core.network.WikiResult
import br.albuquerque.core.viewmodel.WikiViewModel
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val favoriteUseCase: FavoriteUseCase
): WikiViewModel(), PersonHandler {

    val favorites: LiveData<List<PersonUI>> = getFavoritesUseCase.invoke()
    val onHandleFavorite = MutableLiveData<Pair<Int?, String>>()

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

    }
}