package com.example.twg_android_test.search.ui.result

import com.example.twg_android_test.search.data.ImageGroup
import com.example.twg_android_test.search.data.PriceInfo
import com.example.twg_android_test.search.data.Product
import com.example.twg_android_test.search.data.RemoteDataSource
import com.example.twg_android_test.search.data.SearchResult
import com.example.twg_android_test.search.data.SearchResultRepository
import com.example.twg_android_test.tools.NetworkModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

val fakeProduct = Product(
    productName = "Fake Dress",
    priceInfo = PriceInfo(price = 30.0),
    imageGroups = listOf(ImageGroup(imageUrls = listOf("https://example.com/image.jpg"))),
    productImageUrl = "https://example.com/image.jpg",
    productId = "F123",
    productBarcode = "1234567890",
    productDescription = "Fake product description",
    soldOnline = "Y",
    promotions = emptyList()
)

open class FakeSearchResultRepository :
    SearchResultRepository(RemoteDataSource(NetworkModule.warehouseService)) {
    var total: Int = 4

    override fun getSearchResult(
        keyword: String,
        startIndex: Int,
        itemsPerPage: Int
    ): Flow<SearchResult> {
        return flow {
            emit(
                SearchResult(
                    total = total,
                    products = listOf(fakeProduct)
                )
            )
        }
    }
}

@ExperimentalCoroutinesApi
class SearchResultViewModelTest {

    private lateinit var viewModel: SearchResultViewModel
    private lateinit var fakeRepository: FakeSearchResultRepository

    @Before
    fun setup() {
        fakeRepository = FakeSearchResultRepository()
        viewModel = SearchResultViewModel(repository = fakeRepository)
    }

    @Test
    fun testRefreshSearch() = runTest {

        viewModel.refreshSearch("dress")

        advanceUntilIdle()

        val state = viewModel.uiState.first { it.products.isNotEmpty() }

        assertEquals(1, state.products.size)
        assertEquals(4, state.totalItems)
        assertEquals(SearchResultAdapter.LOADING_COMPLETE, state.loadState)
    }

    @Test
    fun testLoadMore_whenMoreDataAvailable() = runTest {

        viewModel.refreshSearch("dress")
        advanceUntilIdle()
        var state = viewModel.uiState.first { it.products.isNotEmpty() }
        assertEquals(1, state.products.size)

        viewModel.loadMore("dress")
        advanceUntilIdle()

        state = viewModel.uiState.first { it.products.size == 2 }
        assertEquals(2, state.products.size)
        assertEquals(4, state.totalItems)
        assertEquals(SearchResultAdapter.LOADING_COMPLETE, state.loadState)
    }

    @Test
    fun testLoadMore_whenNoMoreData() = runTest {
        fakeRepository.total = 1
        viewModel.refreshSearch("dress")
        advanceUntilIdle()
        val stateAfterRefresh = viewModel.uiState.first { it.products.isNotEmpty() }
        assertEquals(1, stateAfterRefresh.products.size)

        viewModel.loadMore("dress")
        advanceUntilIdle()
        val state = viewModel.uiState.first()
        assertEquals(1, state.products.size)
        assertEquals(1, state.totalItems)
        assertEquals(SearchResultAdapter.LOADING_END, state.loadState)
    }
}