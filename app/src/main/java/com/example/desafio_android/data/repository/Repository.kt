package com.example.desafio_android.data.repository

import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.desafio_android.data.api.RequestApi
import com.example.desafio_android.data.db.FavoriteDao
import com.example.desafio_android.data.db.FavoritePeople
import com.example.desafio_android.data.model.People
import com.example.desafio_android.data.paging.PeoplePagingSource
import com.example.desafio_android.util.Resultado
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import java.net.ConnectException
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val requestApi: RequestApi,
    private val favoriteDao: FavoriteDao
) {

    fun getPeople(): Flow<PagingData<People>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PeoplePagingSource(requestApi, null) }
        ).flow
    }

    fun searchPeople(search: String): Flow<PagingData<People>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PeoplePagingSource(requestApi, search) }
        ).flow
    }

    suspend fun getNamePlanet(url: String) = liveData {
        try {
            val resposta = requestApi.getNamePlanet(url)
            if (resposta.isSuccessful) {
                emit(Resultado.Sucesso(dado = resposta.body()))
            } else {
                emit(Resultado.Erro(exception = Exception("Falha ao carregar nome")))
            }
        } catch (e: ConnectException) {
            emit(Resultado.Erro(exception = Exception("Falha na comunicação com a API")))
        } catch (e: Exception) {
            emit(Resultado.Erro(exception = e))
        }
    }

    suspend fun getNameSpecie(url: String) = liveData {
        try {
            val resposta = requestApi.getNameSpecies(url)
            if (resposta.isSuccessful) {
                emit(Resultado.Sucesso(dado = resposta.body()))
            } else {
                emit(Resultado.Erro(exception = Exception("Falha ao carregar nome")))
            }
        } catch (e: ConnectException) {
            emit(Resultado.Erro(exception = Exception("Falha na comunicação com a API")))
        } catch (e: Exception) {
            emit(Resultado.Erro(exception = e))
        }
    }

    suspend fun addFavoriteApi(id: Int) = liveData {
        val resposta = requestApi.addFavoriteApi(id)
        if (resposta.code() == 201) {
            emit(Resultado.Sucesso(dado = resposta.body()!!.message))
        } else if (resposta.code() == 400)  {
            emit(Resultado.Sucesso(dado = resposta.body()!!.errorMessage))
        } else {
            emit(Resultado.Erro(exception = Exception("Falha na comunicação com a API")))
        }
    }

    suspend fun addToFavorite(name: String, people: People) =
        favoriteDao.addToFavorite(FavoritePeople(name, people))

    fun getFavorites() = favoriteDao.getFavorites()
    suspend fun checkPeople(people: People) = favoriteDao.checkPeople(people.name)
    suspend fun removeFromFavorite(people: People) = favoriteDao.removeFromFavorite(people)

}