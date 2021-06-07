package com.knowledge.wikisw_luan.data.repository

import com.knowledge.wikisw_luan.data.SwCloud
import com.knowledge.wikisw_luan.data.cache.CharacterDAO
import com.knowledge.wikisw_luan.data.mapper.SwMapper
import com.knowledge.wikisw_luan.models.CharacterModel

class SwRepository(
    private val cloud: SwCloud, private val cache: CharacterDAO
) {
    suspend fun getCharacters() : List<CharacterModel> {
        val response = cloud.getCharacters()
        cache.insertAll(SwMapper.characterResponseToEntity(response.results, "", "", false))
      //  val planets = response.results.map { it.homeworld }
      //  val species = response.results.map { it.species.first() }
        return SwMapper.characterResponseToModel(response.results, "", "")
    }
}