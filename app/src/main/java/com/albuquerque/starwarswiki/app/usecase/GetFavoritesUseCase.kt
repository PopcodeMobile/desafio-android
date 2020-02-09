package com.albuquerque.starwarswiki.app.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.albuquerque.starwarswiki.app.model.mapper.toUI
import com.albuquerque.starwarswiki.app.model.ui.PersonUI
import com.albuquerque.starwarswiki.app.repository.IWikiRepository
import com.albuquerque.starwarswiki.core.usecase.ObservableUseCase

class GetFavoritesUseCase(private val wikiRepository: IWikiRepository): ObservableUseCase() {

    fun invoke(): LiveData<List<PersonUI>> {
        return wikiRepository.getPeopleFromDB(true).map { list ->
            list.map { it.toUI() } }
    }

}