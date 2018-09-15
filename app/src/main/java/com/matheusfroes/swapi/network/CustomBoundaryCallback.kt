package com.matheusfroes.swapi.network

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.data.source.LocalSource
import com.matheusfroes.swapi.data.source.RemoteSource
import com.matheusfroes.swapi.extra.Result
import kotlinx.coroutines.experimental.launch

class CustomBoundaryCallback(
        private val remote: RemoteSource,
        private val local: LocalSource,
        private val query: String? = null
) : PagedList.BoundaryCallback<Person>() {
    private var apiPage = 1

    val networkState = MutableLiveData<Result<Any>>()

    override fun onZeroItemsLoaded() {
        fetchNextPage()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Person) {
        fetchNextPage()
    }

    private fun fetchNextPage() {
        launch {
            try {
                networkState.postValue(Result.InProgress())
                val people = remote.getPeople(apiPage, query)
                local.savePeople(people)
                apiPage++
                networkState.postValue(Result.Complete(Any()))
            } catch (e: Exception) {
                networkState.postValue(Result.Error(e))
            }

        }
    }
}