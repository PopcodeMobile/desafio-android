package com.example.desafio_android.data.paging

import androidx.paging.PagingSource
import com.example.desafio_android.data.api.RequestApi
import com.example.desafio_android.data.model.People
import retrofit2.HttpException
import java.io.IOException

private const val PAGE_INDEX = 1

class PeoplePagingSource(
    private val requestApi: RequestApi
): PagingSource<Int, People>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, People> {

        return try {
            val pagePosition = params.key ?: PAGE_INDEX
            val response = requestApi.getPeople(pagePosition)
            val people = response.body()?.people

            LoadResult.Page(
                data = people!!,
                prevKey = if (pagePosition == PAGE_INDEX) null else pagePosition - 1,
                nextKey = if (people.isEmpty()) null else pagePosition + 1
            )

        } catch (e: IOException){
            LoadResult.Error(e)
        }catch (e: HttpException){
            LoadResult.Error(e)
        }

    }
}