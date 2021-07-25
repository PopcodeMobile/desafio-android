package com.example.desafio_android.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.desafio_android.data.api.RequestApi
import com.example.desafio_android.data.model.People
import com.example.desafio_android.data.paging.PeoplePagingSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val requestApi: RequestApi){

    fun getPeople(): Flow<PagingData<People>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PeoplePagingSource(requestApi, null) }
        ).flow
    }

    fun searchPeople(search: String): Flow<PagingData<People>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PeoplePagingSource(requestApi, search)}
        ).flow
    }

}