package com.albuquerque.starwarswiki.repository

import com.albuquerque.starwarswiki.core.network.WikiResult
import com.albuquerque.starwarswiki.core.repository.WikiRemoteRepository
import com.albuquerque.starwarswiki.model.dto.ResponsePeople

class WikiRemoteRepository: WikiRemoteRepository(), IWikiRemoteDataSource {

    private val API by lazy { getRetrofitBuilder().create(WikiAPI::class.java) }

    override suspend fun getPeople(): WikiResult<ResponsePeople> {
        return executeRequest(API) { fetchPeople() }
    }

}