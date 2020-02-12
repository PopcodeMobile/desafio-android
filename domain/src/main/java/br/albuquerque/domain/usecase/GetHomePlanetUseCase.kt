package br.albuquerque.domain.usecase

import br.albuquerque.core.network.WikiResult
import br.albuquerque.core.usecase.ObservableUseCase
import br.albuquerque.data.ui.PlanetUI
import br.albuquerque.domain.repository.IWikiRepository
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