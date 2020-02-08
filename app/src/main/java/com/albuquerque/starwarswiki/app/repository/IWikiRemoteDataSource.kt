package com.albuquerque.starwarswiki.app.repository

import com.albuquerque.starwarswiki.app.model.dto.ResponseFavorite
import com.albuquerque.starwarswiki.core.network.WikiResult
import com.albuquerque.starwarswiki.app.model.dto.ResponsePeople

interface IWikiRemoteDataSource {

    suspend fun getPeople(): WikiResult<ResponsePeople>
    suspend fun favorite(): WikiResult<ResponseFavorite>

}