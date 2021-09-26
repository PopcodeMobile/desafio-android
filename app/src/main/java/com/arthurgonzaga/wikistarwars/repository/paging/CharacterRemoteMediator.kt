package com.arthurgonzaga.wikistarwars.repository.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.arthurgonzaga.wikistarwars.api.responses.CharacterResponse
import com.arthurgonzaga.wikistarwars.api.services.PeopleService
import com.arthurgonzaga.wikistarwars.data.WikiStarWarsDB
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity
import com.arthurgonzaga.wikistarwars.data.model.RemoteKeys
import com.arthurgonzaga.wikistarwars.util.toEntity
import retrofit2.HttpException
import retrofit2.await
import java.io.IOException
import java.io.InvalidObjectException


@ExperimentalPagingApi
class CharacterRemoteMediator(
    private val service: PeopleService,
    private val database: WikiStarWarsDB
) : RemoteMediator<Int, CharacterEntity>() {

    // Set to refresh every time i
    override suspend fun initialize(): InitializeAction {
        Log.i(TAG, "initializing")
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {

        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        return try {

            // Get the page result
            val response = service.getPeoplePage(page).await()
            val isEndOfList = response.results.isEmpty()
            database.withTransaction {




                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1

                val keys = response.results.map {
                    RemoteKeys(characterId = it.getId(), prevKey = prevKey, nextKey = nextKey)
                }

                val characters = response.results.map {
                    it.toEntity()
                }

                if (loadType == LoadType.REFRESH) {
                    // clear all the rows in the database
                    database.remoteKeysDAO().clearRemoteKeys()

                    // clear all the rows with isFavorite = false
                    // update all the rows with isFavorite = true
                    database.charactersDAO().upsert(characters)
                }

                database.remoteKeysDAO().insertAll(keys)
                database.charactersDAO().insertAll(characters)
            }

            MediatorResult.Success(
                endOfPaginationReached = response.getNextPageIndex() == null
            )
        } catch (e: IOException) {
            Log.e(TAG, "load: ", e)
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            Log.e(TAG, "load: ", e)
            MediatorResult.Error(e)
        }
    }

    /**
     * this returns the page key or the final end of list success result
     */
    suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, CharacterEntity>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                Log.i(TAG, "LoadType.REFRESH")
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.APPEND -> {
                Log.i(TAG, "LoadType.APPEND")
                val remoteKeys = getLastRemoteKey(state)
                    ?: throw InvalidObjectException("Remote key should not be null for $loadType")
                remoteKeys.nextKey
            }
            LoadType.PREPEND -> {
                Log.i(TAG, "LoadType.PREPEND")
                val remoteKeys = getFirstRemoteKey(state)
                    ?: throw InvalidObjectException("Invalid state, key should not be null")
                //end of list condition reached
                remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
        }
    }


    /**
     * get the last remote key inserted which had the data
     */
    private suspend fun getLastRemoteKey(state: PagingState<Int, CharacterEntity>): RemoteKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { character -> database.remoteKeysDAO().remoteKeysById(character.id) }
            ?: RemoteKeys(0, null, 2)
    }

    /**
     * get the first remote key inserted which had the data
     */
    private suspend fun getFirstRemoteKey(state: PagingState<Int, CharacterEntity>): RemoteKeys? {
        return state.pages
            .firstOrNull() { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { character -> database.remoteKeysDAO().remoteKeysById(character.id) }
            ?: RemoteKeys(0, null, 2)
    }

    /**
     * get the closest remote key inserted which had the data
     */
    private suspend fun getClosestRemoteKey(state: PagingState<Int, CharacterEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysDAO().remoteKeysById(id)
            }
        }
    }


    companion object {
        private const val STARTING_PAGE_INDEX = 1
        private const val TAG = "CharacterRemoteMediator"
    }
}