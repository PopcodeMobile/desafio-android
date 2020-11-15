package br.com.example.starwars.domain.repository

import androidx.paging.PagingData
import br.com.example.starwars.domain.entities.People
import kotlinx.coroutines.flow.Flow

interface GetPeopleListRepository {
    suspend fun getPeopleList(): Flow<PagingData<People>>
}