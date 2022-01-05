package br.com.example.starwars.data.repository

import androidx.paging.*
import br.com.example.starwars.data.local.AppDataBase
import br.com.example.starwars.data.remote.ApiService
import br.com.example.starwars.data.repository.paging.PeoplePagingSource
import br.com.example.starwars.data.repository.paging.PeopleRemoteMediator
import br.com.example.starwars.domain.entities.People
import br.com.example.starwars.domain.repository.GetPeopleListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPeopleListRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dataBase: AppDataBase
) : GetPeopleListRepository {

    @ExperimentalPagingApi
    override suspend fun getPeopleList(): Flow<PagingData<People>> {
        return Pager(
            PagingConfig(pageSize = 10, enablePlaceholders = false, prefetchDistance = 3),
            remoteMediator = PeopleRemoteMediator(apiService, dataBase),
            pagingSourceFactory = { PeoplePagingSource(apiService) }
        ).flow
            .map { people ->
                people.map { it.apiPeopleToPeople(it) }
            }
    }
}