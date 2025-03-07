package com.example.twg_android_test.search.data

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("products")
    val products: List<Product>?,
    @SerializedName("total")
    val total: Int?
)