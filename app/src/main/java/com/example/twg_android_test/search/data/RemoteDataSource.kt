package com.example.twg_android_test.search.data

import com.example.twg_android_test.tools.TWGService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class RemoteDataSource(private val service: TWGService) {
    fun getSearchResult(keyword: String, startIndex: Int, limit: Int): Flow<SearchResult> = flow {
        val params = mapOf(
            "Search" to keyword,
            "Start" to startIndex.toString(),
            "Limit" to limit.toString(),
            "Want" to "Search"
        )

        val response = service.getSearchResult(params)
        if (response.isSuccessful) {
            val body = response.body()
            val json = Gson().toJson(body)
            body?.let { emit(it) } ?: throw Exception("Empty response body")
        } else {
            throw Exception("Search failed: ${response.message()}")
        }
    }
}