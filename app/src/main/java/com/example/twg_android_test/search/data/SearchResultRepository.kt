package com.example.twg_android_test.search.data

import kotlinx.coroutines.flow.Flow

open class SearchResultRepository(
    private val remoteDataSource: RemoteDataSource
) {
    open fun getSearchResult(keyword: String, startIndex: Int, limit: Int): Flow<SearchResult> {
        return remoteDataSource.getSearchResult(keyword, startIndex, limit)
    }
}