package com.example.twg_android_test.search.data

import com.example.twg_android_test.tools.TWGService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class RemoteDataSourceTest {

    class FakeWarehouseService : TWGService {
        override suspend fun loginAsGuest(auth: String, device: String): Response<Void> {
            return Response.success(null)
        }

        override suspend fun getSearchResult(params: Map<String, String>): Response<SearchResult> {
            val fakeProduct = Product(
                productName = "Fake Dress",
                priceInfo = PriceInfo(price = 30.0),
                imageGroups = listOf(ImageGroup(imageUrls = listOf("https://example.com/image1.jpg"))),
                productImageUrl = "https://example.com/image1.jpg",
                productId = "F123",
                productBarcode = "1234567890",
                productDescription = "Fake product description",
                soldOnline = "Y",
                promotions = null
            )
            val fakeSearchResult = SearchResult(
                products = listOf(fakeProduct),
                total = 1
            )
            return Response.success(fakeSearchResult)
        }
    }

    @Test
    fun testGetSearchResult_emitsFakeSearchResult() = runTest {
        val fakeService = FakeWarehouseService()
        val remoteDataSource = RemoteDataSource(fakeService)
        val keyword = "dress"

        val result = remoteDataSource.getSearchResult(keyword, 0, 1).first()

        assertEquals(1, result.total)
        val products = result.products
        assertEquals(1, products?.size)
        assertEquals("Fake Dress", products?.first()?.productName)
    }
}