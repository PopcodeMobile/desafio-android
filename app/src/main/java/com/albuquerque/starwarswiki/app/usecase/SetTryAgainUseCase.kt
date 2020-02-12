package com.albuquerque.starwarswiki.app.usecase

import com.albuquerque.starwarswiki.app.model.ui.PersonUI
import com.albuquerque.starwarswiki.app.repository.IWikiRepository
import com.albuquerque.starwarswiki.core.usecase.ObservableUseCase
import kotlinx.coroutines.withContext

class SetTryAgainUseCase(private val wikiRepository: IWikiRepository): ObservableUseCase() {

    suspend fun invoke(personUI: PersonUI) {
        withContext(coroutineContext) { wikiRepository.updatePerson(personUI) }
    }

}