package br.com.example.starwars.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.example.starwars.data.entities.ApiPeople
import br.com.example.starwars.data.remote.ApiService
import retrofit2.HttpException
import java.io.IOException

class PeoplePagingSource(
    private val apiService: ApiService
) : PagingSource<Int, ApiPeople>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiPeople> {
        return try {
            val position = params.key ?: PAGE_INDEX
            val response = apiService.getListPeople(page = position)

            LoadResult.Page(
                response.people,
                if (position == PAGE_INDEX) null else position - 1,
                position.plus(1)
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ApiPeople>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val PAGE_INDEX = 1
    }
}