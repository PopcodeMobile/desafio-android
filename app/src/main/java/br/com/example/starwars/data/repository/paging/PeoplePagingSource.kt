package br.com.example.starwars.data.repository.paging

import androidx.paging.PagingSource
import br.com.example.starwars.data.entities.ApiPeople
import br.com.example.starwars.data.remote.ApiService
import retrofit2.HttpException
import java.io.IOException

class PeoplePagingSource(
    private val apiService: ApiService
) : PagingSource<Int, ApiPeople>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiPeople> {
        return try {
            val response = params.key?.let { apiService.getListPeople(page = it) }

            LoadResult.Page(
                response?.people ?: listOf(),
                params.key?.minus(1),
                params.key?.plus(1)
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}