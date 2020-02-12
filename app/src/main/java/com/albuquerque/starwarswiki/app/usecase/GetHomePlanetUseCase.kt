package com.albuquerque.starwarswiki.app.usecase

import com.albuquerque.starwarswiki.app.model.ui.PlanetUI
import com.albuquerque.starwarswiki.app.repository.IWikiRepository
import com.albuquerque.starwarswiki.core.network.WikiResult
import com.albuquerque.starwarswiki.core.usecase.ObservableUseCase
import kotlinx.coroutines.withContext

class GetHomePlanetUseCase(private val wikiRepository: IWikiRepository): ObservableUseCase() {

    suspend fun invoke(id: String): WikiResult<PlanetUI> {

        val result = withContext(coroutineContext) {
            wikiRepository.getPlanetHome(id)
        }

        return when(result) {

            is WikiResult.Success -> WikiResult.Success(result.data)

            is WikiResult.Failure -> WikiResult.Failure(result.error)

        }

    }

}