package com.albuquerque.starwarswiki.repository

import com.albuquerque.starwarswiki.core.network.WikiResult
import com.albuquerque.starwarswiki.model.dto.ResponsePeople

interface IWikiRemoteDataSource {

    suspend fun getPeople(): WikiResult<ResponsePeople>

}