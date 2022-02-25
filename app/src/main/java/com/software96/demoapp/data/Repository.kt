package com.software96.demoapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.software96.demoapp.data.paging.PagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val api: Api) {

    fun getSearchResults(query: String) =
        Pager(config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ), pagingSourceFactory = { PagingSource(api, query) }
        ).liveData
}