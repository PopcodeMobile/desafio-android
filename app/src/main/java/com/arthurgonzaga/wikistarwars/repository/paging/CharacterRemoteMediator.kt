package com.arthurgonzaga.wikistarwars.repository.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.rxjava3.RxRemoteMediator
import androidx.room.withTransaction
import com.arthurgonzaga.wikistarwars.api.responses.CharacterResponse
import com.arthurgonzaga.wikistarwars.api.responses.SpecieResponse
import com.arthurgonzaga.wikistarwars.api.services.HomeWorldService
import com.arthurgonzaga.wikistarwars.api.services.PeopleService
import com.arthurgonzaga.wikistarwars.api.services.SpeciesService
import com.arthurgonzaga.wikistarwars.data.WikiStarWarsDB
import com.arthurgonzaga.wikistarwars.data.dao.CharacterDAO
import com.arthurgonzaga.wikistarwars.data.dao.RemoteKeysDAO
import com.arthurgonzaga.wikistarwars.data.model.CharacterEntity
import com.arthurgonzaga.wikistarwars.data.model.RemoteKeys
import com.arthurgonzaga.wikistarwars.util.toEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import retrofit2.HttpException
import retrofit2.await
import java.io.IOException
import java.io.InvalidObjectException


@ExperimentalPagingApi
class CharacterRemoteMediator(
    private val peopleService: PeopleService,
    private val speciesService: SpeciesService,
    private val homeWorldService: HomeWorldService,
    private val database: WikiStarWarsDB,
    private val query: String,
) : RxRemoteMediator<Int, CharacterEntity>() {

    override fun initializeSingle(): Single<InitializeAction> {
        return Single.just(InitializeAction.LAUNCH_INITIAL_REFRESH)
    }

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                when (it) {
                    LoadType.REFRESH -> {
                        Log.i(TAG, "LoadType.REFRESH")
                        val remoteKeys = getClosestRemoteKey(state)
                        remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
                    }
                    LoadType.APPEND -> {
                        Log.i(TAG, "LoadType.APPEND")
                        val remoteKeys = getLastRemoteKey(state)
                            ?: throw InvalidObjectException("Remote key should not be null for $it")
                        remoteKeys.nextKey ?: INVALID_PAGE
                    }
                    LoadType.PREPEND -> {
                        Log.i(TAG, "LoadType.PREPEND")
                        val remoteKeys = getFirstRemoteKey(state)
                            ?: throw InvalidObjectException("Invalid state, key should not be null")
                        //end of list condition reached
                        remoteKeys.prevKey ?: INVALID_PAGE
                    }
                }
            }.flatMap { page ->
                if (page == INVALID_PAGE) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                } else {
                    getCharactersPage(page).map { insertOnDB(page, loadType, it) }
                        .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = it.isEmpty()) }
                        .onErrorReturn { MediatorResult.Error(it) }
                }
            }.onErrorReturn { MediatorResult.Error(it) }
    }



    @Suppress("DEPRECATION")
    fun insertOnDB(
        page: Int,
        loadType: LoadType,
        characters: List<CharacterEntity>,
    ) : List<CharacterEntity>{
        database.beginTransaction()

        try {
            if (loadType == LoadType.REFRESH) {
                // clear all the rows in the database
                database.remoteKeysDAO().clearRemoteKeys()

                // clear all the rows with isFavorite = false
                // update all the rows with isFavorite = true
                database.charactersDAO().upsert(characters)
            }

            val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
            var nextKey = if (characters.isEmpty()) null else page + 1

            val keys = characters.map {
                RemoteKeys(characterId = it.id, prevKey = prevKey, nextKey = nextKey)
            }


            database.remoteKeysDAO().insertAll(keys)
            database.charactersDAO().insertAll(characters)
            database.setTransactionSuccessful()
        }finally {
            database.endTransaction()
        }

        return characters
    }

    fun getCharactersPage(page: Int): Single<MutableList<CharacterEntity>> {
        return peopleService.getPeoplePage(page, query).map { it.results }
            .flattenAsObservable { list ->
                list
            }
            .flatMap { characterResponse ->

                val homeWorld =
                    homeWorldService.getHomeWorld(characterResponse.getHomeWorldId())
                val specie =
                    characterResponse.getSpecieId()?.let { speciesService.getSpecie(it) }
                        ?.subscribeOn(Schedulers.newThread()) ?: Single.just(
                        SpecieResponse(
                            "Human"
                        )
                    )

                Observable.zip(
                    homeWorld.toObservable(),
                    specie.toObservable()
                ) { homeWorldResponse, specieResponse ->
                    characterResponse.toEntity(
                        homeWorldName = homeWorldResponse.name,
                        specieName = specieResponse.name
                    )
                }
            }
            .toList()
    }

    /**
     * get the last remote key inserted which had the data
     */
    private fun getLastRemoteKey(state: PagingState<Int, CharacterEntity>): RemoteKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { character -> database.remoteKeysDAO().remoteKeysById(character.id) }
            ?: RemoteKeys(0, null, 2)
    }

    /**
     * get the first remote key inserted which had the data
     */
    private fun getFirstRemoteKey(state: PagingState<Int, CharacterEntity>): RemoteKeys? {
        return state.pages
            .firstOrNull() { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { character -> database.remoteKeysDAO().remoteKeysById(character.id) }
            ?: RemoteKeys(0, null, 2)
    }

    /**
     * get the closest remote key inserted which had the data
     */
    private fun getClosestRemoteKey(state: PagingState<Int, CharacterEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysDAO().remoteKeysById(id)
            }
        }
    }


    companion object {
        private const val STARTING_PAGE_INDEX = 1
        private const val TAG = "CharacterRemoteMediator"
        private const val INVALID_PAGE = -1
    }
}