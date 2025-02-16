package com.avito.tech.intern.data.network

import ChartResponse
import com.avito.tech.intern.data.dto.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface DeezerAPI {

    @GET("/chart")
    suspend fun getChart() : Response<ChartResponse>

    @GET("/search")
    suspend fun getSearch(@Query("q") query: String) : Response<SearchResponse>
}