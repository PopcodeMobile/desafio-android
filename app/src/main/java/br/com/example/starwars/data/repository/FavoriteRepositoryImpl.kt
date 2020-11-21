package br.com.example.starwars.data.repository

import br.com.example.starwars.data.local.PeopleDao
import br.com.example.starwars.data.remote.ApiService
import br.com.example.starwars.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val peopleDao: PeopleDao,
    private val apiService: ApiService
) : FavoriteRepository {

    override suspend fun addAndRemoveFavorite(favorite: Boolean, id: Int) {
        withContext(Dispatchers.IO) {
            peopleDao.updatePerson(favorite, id)
        }
    }
}