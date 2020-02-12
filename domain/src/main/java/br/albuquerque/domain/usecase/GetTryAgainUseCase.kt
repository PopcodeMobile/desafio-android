package br.albuquerque.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import br.albuquerque.core.usecase.ObservableUseCase
import br.albuquerque.data.util.toUI
import br.albuquerque.data.ui.PersonUI
import br.albuquerque.domain.repository.IWikiRepository

class GetTryAgainUseCase(private val wikiRepository: IWikiRepository): ObservableUseCase() {

    fun invoke(): LiveData<MutableList<PersonUI>> {
        return wikiRepository.getTryAgainFromDB().map { list ->
            list.map { it.toUI() }.toMutableList() }
    }

}