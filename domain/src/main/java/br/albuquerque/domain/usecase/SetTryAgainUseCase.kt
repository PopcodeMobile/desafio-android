package br.albuquerque.domain.usecase

import br.albuquerque.core.usecase.ObservableUseCase
import br.albuquerque.data.ui.PersonUI
import br.albuquerque.domain.repository.IWikiRepository
import kotlinx.coroutines.withContext

class SetTryAgainUseCase(private val wikiRepository: IWikiRepository): ObservableUseCase() {

    suspend fun invoke(personUI: PersonUI) {
        withContext(coroutineContext) { wikiRepository.updatePerson(personUI) }
    }

}