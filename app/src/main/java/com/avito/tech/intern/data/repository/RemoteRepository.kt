package com.avito.tech.intern.data.repository

import ChartResponse
import android.icu.text.StringSearch
import com.avito.tech.intern.data.dto.SearchResponse
import com.avito.tech.intern.data.network.DeezerAPI
import retrofit2.Response
import retrofit2.http.Query

class RemoteRepository(
    private val deezerAPI: DeezerAPI
){

    suspend fun getChart() : Response<ChartResponse> {
        return deezerAPI.getChart()
    }

    suspend fun getSearch( query : String) : Response<SearchResponse>{
        return deezerAPI.getSearch(query)
    }
}