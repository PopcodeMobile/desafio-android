package br.albuquerque.domain.usecase

import br.albuquerque.core.network.WikiResult
import br.albuquerque.core.usecase.ObservableUseCase
import br.albuquerque.data.ui.SpeciesUI
import br.albuquerque.domain.repository.IWikiRepository
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