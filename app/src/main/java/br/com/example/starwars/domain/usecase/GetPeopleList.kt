package br.com.example.starwars.domain.usecase

import androidx.paging.ExperimentalPagingApi
import br.com.example.starwars.domain.repository.GetPeopleListRepository
import javax.inject.Inject

class GetPeopleList @Inject constructor(
    private val repository: GetPeopleListRepository
) {
    @ExperimentalPagingApi
    suspend fun execute() = repository.getPeopleList()
}