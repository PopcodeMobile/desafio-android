package br.albuquerque.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import br.albuquerque.core.usecase.ObservableUseCase
import br.albuquerque.data.util.toUI
import br.albuquerque.data.ui.PersonUI
import br.albuquerque.domain.repository.IWikiRepository

class GetFavoritesUseCase(private val wikiRepository: IWikiRepository): ObservableUseCase() {

    fun invoke(): LiveData<List<PersonUI>> {
        return wikiRepository.getPeopleFromDB(true).map { list ->
            list.map { it.toUI() } }
    }

}