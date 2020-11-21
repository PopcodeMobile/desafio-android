package br.com.example.starwars.domain.repository

interface FavoriteRepository {
    suspend fun addAndRemoveFavorite(favorite: Boolean, id: Int)
}