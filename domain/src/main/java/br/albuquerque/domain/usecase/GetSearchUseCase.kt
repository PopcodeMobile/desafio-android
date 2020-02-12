package br.albuquerque.domain.usecase

import br.albuquerque.core.network.WikiResult
import br.albuquerque.core.usecase.PaginationUseCase
import br.albuquerque.data.ui.PersonUI
import br.albuquerque.domain.repository.IWikiRepository
import kotlinx.coroutines.withContext

class GetSearchUseCase(private val wikiRepository: IWikiRepository): PaginationUseCase() {

    suspend fun invoke(search: String): WikiResult<List<PersonUI>> {

        val result = withContext(coroutineContext) {
            wikiRepository.search(search)
        }

        return when(result) {

            is WikiResult.Success -> WikiResult.Success(result.data)

            is WikiResult.Failure -> WikiResult.Failure(result.error)

        }

    }

}