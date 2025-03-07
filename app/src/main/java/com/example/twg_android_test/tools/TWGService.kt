package com.example.twg_android_test.tools

import com.example.twg_android_test.search.data.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface TWGService {

    @GET("Login.json")
    suspend fun loginAsGuest(
        @Header("Authorization") auth: String,
        @Header("X-TWL-Device") device: String
    ): Response<Void>

    @GET("Search.json")
    suspend fun getSearchResult(
        @QueryMap params: Map<String, String>
    ): Response<SearchResult>
}