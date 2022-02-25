package com.software96.demoapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.software96.demoapp.data.Api
import com.software96.demoapp.data.model.GitRepo
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class PagingSource(
    private val _api: Api,
    private val _query: String
) : PagingSource<Int, GitRepo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GitRepo> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = _api.getTrendingRepos(_query, position, params.loadSize)
            val repos = response.items

            LoadResult.Page(
                data = repos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (repos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GitRepo>): Int? = 0

}