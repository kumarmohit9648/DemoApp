package com.software96.demoapp.data

import com.software96.demoapp.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("search/repositories?sort=stars")
    suspend fun getTrendingRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): SearchResponse
}