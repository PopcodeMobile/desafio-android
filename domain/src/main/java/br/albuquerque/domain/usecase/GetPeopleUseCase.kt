package br.albuquerque.domain.usecase

import androidx.lifecycle.map
import br.albuquerque.core.network.WikiResult
import br.albuquerque.core.usecase.PaginationUseCase
import br.albuquerque.data.util.toUI
import br.albuquerque.domain.repository.IWikiRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class GetPeopleUseCase(private val wikiRepository: IWikiRepository): PaginationUseCase() {

    operator fun invoke(shouldClearTable: Boolean, page: Int) = flow {

        emit(wikiRepository.getPeopleFromDB().map { list ->
            list.map { it.toUI() }
        })

        val result = withContext(coroutineContext) {
            wikiRepository.getPeople(shouldClearTable, page)
        }

        if (result is WikiResult.Failure) {
            throw result.error
        } else {
            (result as? WikiResult.Success?)?.let { _onPageSuccessfull(it.data.size) }
        }

    }

}