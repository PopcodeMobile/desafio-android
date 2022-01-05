package br.com.example.starwars.data.repository.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import br.com.example.starwars.data.entities.ApiPeople
import br.com.example.starwars.data.entities.RemoteKeys
import br.com.example.starwars.data.local.AppDataBase
import br.com.example.starwars.data.remote.ApiService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PeopleRemoteMediator(
    private val apiService: ApiService,
    private val dataBase: AppDataBase
) : RemoteMediator<Int, ApiPeople>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ApiPeople>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }

        return try {
            val response = apiService.getListPeople(page = page)
            val endOfPaginationReached = response.people.isEmpty()

            dataBase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dataBase.remoteKeysDao().clearRemoteKeys()
                    dataBase.peopleDao().clearPeople()
                }
                val prevKey = if (page == PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val key = RemoteKeys(prevKey = prevKey, nextKey = nextKey)
                dataBase.remoteKeysDao().insert(key)
                dataBase.peopleDao().insertAll(response.people)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ApiPeople>): RemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                repo.id?.toLong()?.let { dataBase.remoteKeysDao().remoteKeysRepoId(it) }
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ApiPeople>): RemoteKeys? {
        return state.pages.firstOrNull() { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                repo.id?.toLong()?.let { dataBase.remoteKeysDao().remoteKeysRepoId(it) }
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ApiPeople>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                dataBase.remoteKeysDao().remoteKeysRepoId(repoId.toLong())
            }
        }
    }

    companion object {
        private const val PAGE_INDEX = 1
    }
}