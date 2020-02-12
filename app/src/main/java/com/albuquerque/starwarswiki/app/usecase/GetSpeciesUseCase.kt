package com.albuquerque.starwarswiki.app.usecase

import com.albuquerque.starwarswiki.app.model.ui.SpeciesUI
import com.albuquerque.starwarswiki.app.repository.IWikiRepository
import com.albuquerque.starwarswiki.core.network.WikiResult
import com.albuquerque.starwarswiki.core.usecase.ObservableUseCase
import kotlinx.coroutines.withContext

class GetSpeciesUseCase(private val wikiRepository: IWikiRepository): ObservableUseCase() {

    suspend fun invoke(id: String): WikiResult<SpeciesUI> {

        val result = withContext(coroutineContext) {
            wikiRepository.getSpecies(id)
        }

        return when(result) {

            is WikiResult.Success -> WikiResult.Success(result.data)

            is WikiResult.Failure -> WikiResult.Failure(result.error)

        }

    }

}