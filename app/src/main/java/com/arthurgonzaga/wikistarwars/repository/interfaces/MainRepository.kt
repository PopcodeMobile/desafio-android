package com.arthurgonzaga.wikistarwars.repository.interfaces

interface MainRepository {

    suspend fun favoriteCharacter(characterId: Int): Boolean

    suspend fun unFavoriteCharacter(characterId: Int)

    suspend fun synchronizeFavoriteCharacters(): Boolean
}