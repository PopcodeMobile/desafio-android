package com.albuquerque.starwarswiki.app.usecase

import com.albuquerque.starwarswiki.app.model.ui.PersonUI
import com.albuquerque.starwarswiki.app.repository.IWikiRepository
import com.albuquerque.starwarswiki.core.network.WikiResult
import com.albuquerque.starwarswiki.core.usecase.PaginationUseCase
import kotlinx.coroutines.withContext


class FavoriteUseCase(private val wikiRepository: IWikiRepository): PaginationUseCase() {

    suspend fun invoke(person: PersonUI): WikiResult<String?> {
        person.isFavorite = !person.isFavorite

        if(person.isFavorite) {

            val result = withContext(coroutineContext) {
                wikiRepository.favorite()
            }

            return when(result) {

                is WikiResult.Success -> {
                    withContext(coroutineContext) { wikiRepository.updatePerson(person) }
                    WikiResult.Success(result.data.message)
                }

                is WikiResult.Failure -> WikiResult.Failure(result.error)

            }
        } else {
            withContext(coroutineContext) { wikiRepository.updatePerson(person) }
            return WikiResult.Success("")
        }


    }

}