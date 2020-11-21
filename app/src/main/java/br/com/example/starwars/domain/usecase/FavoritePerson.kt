package br.com.example.starwars.domain.usecase

import br.com.example.starwars.domain.repository.FavoriteRepository
import javax.inject.Inject

class FavoritePerson @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend fun execute(favorite: Boolean, id: Int) = repository.addAndRemoveFavorite(favorite, id)
}