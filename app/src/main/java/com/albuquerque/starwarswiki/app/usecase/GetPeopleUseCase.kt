package com.albuquerque.starwarswiki.app.usecase

import androidx.lifecycle.map
import com.albuquerque.starwarswiki.core.network.WikiResult
import com.albuquerque.starwarswiki.core.usecase.PaginationUseCase
import com.albuquerque.starwarswiki.app.model.mapper.toUI
import com.albuquerque.starwarswiki.app.repository.IWikiRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class GetPeopleUseCase(private val wikiRepository: IWikiRepository): PaginationUseCase() {

    operator fun invoke(shouldClearTable: Boolean) = flow {

        emit(wikiRepository.getPeopleFromDB().map { list ->
            list.map { it.toUI() }
        })

        val result = withContext(coroutineContext) {
            wikiRepository.getPeople(shouldClearTable)
        }

        if (result is WikiResult.Failure) {
            throw result.error
        } else {
            (result as? WikiResult.Success?)?.let { _onPageSuccessfull(it.data.size) }
        }

    }

}