package com.albuquerque.starwarswiki.app.usecase

import androidx.lifecycle.LiveData
import com.albuquerque.starwarswiki.app.model.entity.ConfigEntity
import com.albuquerque.starwarswiki.app.repository.IWikiRepository
import com.albuquerque.starwarswiki.core.usecase.ObservableUseCase

class GetConfigUseCase(private val wikiRepository: IWikiRepository): ObservableUseCase() {

    fun invoke(): LiveData<ConfigEntity?> {
        return wikiRepository.getConfig()
    }
}