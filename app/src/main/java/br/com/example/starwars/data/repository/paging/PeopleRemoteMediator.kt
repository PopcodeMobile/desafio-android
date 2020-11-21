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
        return try {

            val loadKey = when (loadType) {
                LoadType.REFRESH -> RemoteKeys(id = 0, next = 1)
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    getRemoteKeys()
                }
            }

            val response = loadKey?.next?.let { apiService.getListPeople(page = it) }
            if (response != null) {
                dataBase.withTransaction {
                    dataBase.remoteKeysDao()
                        .saveRemoteKeys(RemoteKeys(0, loadKey.next.plus(1)))
                    response.people.let { dataBase.peopleDao().insertAll(it) }
                }
            }
            MediatorResult.Success(endOfPaginationReached = response?.people == null)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeys(): RemoteKeys? {
        return dataBase.remoteKeysDao().getRemoteKeys().firstOrNull()
    }
}