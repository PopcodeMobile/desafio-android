package com.example.desafio_android.data.paging

import android.net.Uri
import androidx.paging.PagingSource
import com.example.desafio_android.data.api.RequestApi
import com.example.desafio_android.data.model.People
import retrofit2.HttpException
import java.io.IOException

private const val PAGE_INDEX = 1

class PeoplePagingSource(
    private val requestApi: RequestApi,
    private val search: String?
) : PagingSource<Int, People>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, People> {

        return try {
            val pagePosition = params.key ?: PAGE_INDEX

            val response = if (search != null) requestApi.searchPeople(search) else
                requestApi.getPeople(pagePosition)

            var nextPagerNumber: Int? = null

            if (response.body()?.next != null) {
                val uri = Uri.parse(response.body()!!.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPagerNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = response.body()!!.people,
                prevKey = if (pagePosition == PAGE_INDEX) null else pagePosition - 1,
                nextKey = nextPagerNumber
            )

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }

    }
}