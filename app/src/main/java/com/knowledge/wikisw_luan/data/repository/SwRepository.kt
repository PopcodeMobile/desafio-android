package com.knowledge.wikisw_luan.data.repository

import com.knowledge.wikisw_luan.data.SwCloud
import com.knowledge.wikisw_luan.data.cache.CharacterDAO
import com.knowledge.wikisw_luan.data.mapper.SwMapper
import com.knowledge.wikisw_luan.models.CharacterModel

class SwRepository(
    private val cloud: SwCloud, private val cache: CharacterDAO
) {

    var page: Int = 1

    suspend fun getCharacters() : List<CharacterModel> {
        val response = cloud.getCharacters(page)
        cache.insertAll(SwMapper.characterResponseToEntity(response.results, "", "", false))
        page++
        if (!response.next.isNullOrEmpty()) {
            getCharacters()
        }
        return SwMapper.characterEntityToModel(cache.getAll())
    }
}