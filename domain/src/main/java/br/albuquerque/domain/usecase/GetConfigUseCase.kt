package br.albuquerque.domain.usecase

import androidx.lifecycle.LiveData
import br.albuquerque.core.usecase.ObservableUseCase
import br.albuquerque.data.entity.ConfigEntity
import br.albuquerque.domain.repository.IWikiRepository

class GetConfigUseCase(private val wikiRepository: IWikiRepository): ObservableUseCase() {

    fun invoke(): LiveData<ConfigEntity?> {
        return wikiRepository.getConfig()
    }
}