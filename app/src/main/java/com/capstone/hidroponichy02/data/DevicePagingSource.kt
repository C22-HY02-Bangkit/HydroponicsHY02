package com.capstone.hidroponichy02.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.capstone.hidroponichy02.response.DataItem
import com.capstone.hidroponichy02.service.ApiService


class DevicePagingSource(private val apiService: ApiService, private val token: String) :
    PagingSource<Int, DataItem>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.devices("Bearer $token", position, params.loadSize).data
            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}