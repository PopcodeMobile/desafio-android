package br.albuquerque.domain.usecase

import br.albuquerque.core.network.WikiResult
import br.albuquerque.core.usecase.ObservableUseCase
import br.albuquerque.data.ui.PersonUI
import br.albuquerque.domain.repository.IWikiRepository
import kotlinx.coroutines.withContext


class FavoriteUseCase(private val wikiRepository: IWikiRepository): ObservableUseCase() {

    suspend fun invoke(person: PersonUI): WikiResult<String?> {
        person.isFavorite = !person.isFavorite

        if(person.isFavorite) {

            if(person.tryAgainPosition != null) person.tryAgainPosition = null

            val result = withContext(coroutineContext) {
                wikiRepository.favorite()
            }

            return when(result) {

                is WikiResult.Success -> {
                    withContext(coroutineContext) { wikiRepository.updatePerson(person) }
                    WikiResult.Success(result.data.message)
                }

                is WikiResult.Failure -> {
                    person.isFavorite = !person.isFavorite
                    WikiResult.Failure(result.error)
                }

            }
        } else {
            withContext(coroutineContext) { wikiRepository.updatePerson(person) }
            return WikiResult.Success("")
        }


    }

}