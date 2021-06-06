package com.knowledge.wikisw_luan.data.repository

import com.knowledge.wikisw_luan.data.SwCloud
import com.knowledge.wikisw_luan.models.CharacterModel

class SwRepository(
    private val cloud: SwCloud
) {
    suspend fun getCharacters() : List<CharacterModel> {
        val response = cloud.getCharacters()
        println(response)
      //  val planets = response.results.map { it.homeworld }
      //  val species = response.results.map { it.species.first() }
        return arrayListOf()
    }
}