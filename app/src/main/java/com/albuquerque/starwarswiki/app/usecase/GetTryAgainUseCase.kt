package com.albuquerque.starwarswiki.app.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.albuquerque.starwarswiki.app.model.mapper.toUI
import com.albuquerque.starwarswiki.app.model.ui.PersonUI
import com.albuquerque.starwarswiki.app.repository.IWikiRepository
import com.albuquerque.starwarswiki.core.usecase.ObservableUseCase

class GetTryAgainUseCase(private val wikiRepository: IWikiRepository): ObservableUseCase() {

    fun invoke(): LiveData<MutableList<PersonUI>> {
        return wikiRepository.getTryAgainFromDB().map { list ->
            list.map { it.toUI() }.toMutableList() }
    }

}