package com.albuquerque.starwarswiki.app.usecase

import com.albuquerque.starwarswiki.app.model.ui.PersonUI
import com.albuquerque.starwarswiki.app.repository.IWikiRepository
import com.albuquerque.starwarswiki.core.network.WikiResult
import com.albuquerque.starwarswiki.core.usecase.PaginationUseCase
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