package com.arthurgonzaga.wikistarwars.repository.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.arthurgonzaga.wikistarwars.api.responses.CharacterResponse
import com.arthurgonzaga.wikistarwars.api.services.PeopleService
import com.arthurgonzaga.wikistarwars.data.WikiStarWarsDB
import com.arthurgonzaga.wikistarwars.data.model.RemoteKeys
import com.arthurgonzaga.wikistarwars.util.toEntity
import retrofit2.HttpException
import retrofit2.await
import java.io.IOException


@ExperimentalPagingApi
class CharacterRemoteMediator(
    private val service: PeopleService,
    private val database: WikiStarWarsDB
) : RemoteMediator<Int, CharacterResponse>() {

    // Set to refresh every time i
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterResponse>
    ): MediatorResult {

        val page: Int = when (loadType) {
            LoadType.REFRESH -> STARTING_PAGE_INDEX

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )

                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                nextKey
            }
        }

        return try {

            // Get the page result
            val response = service.getPeoplePage(page).await()

            database.withTransaction {

                val keys = response.result.map {
                    RemoteKeys(
                        characterId = it.id,
                        nextKey = response.getNextPageIndex(),
                        prevKey = response.getPreviousIndex(),
                    )
                }

                val characters = response.result.map {
                    it.toEntity()
                }

                database.remoteKeysDAO().insertAll(keys)
                database.charactersDAO().insertAll(characters)
            }

            MediatorResult.Success(
                endOfPaginationReached = response.getNextPageIndex() == null
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }


    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CharacterResponse>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { characterResponse ->
                // Get the remote keys of the first items retrieved
                database.remoteKeysDAO().remoteKeysById(characterResponse.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CharacterResponse>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { characterResponse ->
                // Get the remote keys of the last item retrieved
                database.remoteKeysDAO().remoteKeysById(characterResponse.id)
            }
    }


    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}