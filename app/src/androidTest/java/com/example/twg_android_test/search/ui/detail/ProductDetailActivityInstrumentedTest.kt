package com.example.twg_android_test.search.ui.detail

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.twg_android_test.R
import com.example.twg_android_test.search.data.ImageGroup
import com.example.twg_android_test.search.data.PriceInfo
import com.example.twg_android_test.search.data.Product
import org.hamcrest.CoreMatchers.containsString
import org.junit.Test

class ProductDetailActivityInstrumentedTest {

    @Test
    fun productDetailActivityTest() {
        val fakeProduct = Product(
            productName = "Test Dress",
            priceInfo = PriceInfo(price = 50.0),
            imageGroups = listOf(ImageGroup(imageUrls = listOf("https://example.com/test.jpg"))),
            productImageUrl = "https://example.com/test.jpg",
            productId = "T001",
            productBarcode = "9876543210",
            productDescription = "This is a test product description",
            soldOnline = "Y",
            promotions = listOf()
        )

        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            ProductDetailActivity::class.java
        ).apply {
            putExtra(ProductDetailActivity.EXTRA_PRODUCT, fakeProduct)
        }

        ActivityScenario.launch<ProductDetailActivity>(intent)

        onView(withId(R.id.tv_product))
            .check(matches(withText(fakeProduct.productName)))

        onView(withId(R.id.tv_price))
            .check(matches(withText(containsString("50.0"))))

        onView(withId(R.id.tv_barcode))
            .check(matches(withText(fakeProduct.productBarcode)))

        onView(withId(R.id.tv_product_description))
            .check(matches(withText(fakeProduct.productDescription)))

        onView(withId(R.id.tv_product_id))
            .check(matches(withText(containsString(fakeProduct.productId))))

        onView(withId(R.id.tv_sold_online))
            .check(matches(withText(containsString(fakeProduct.soldOnline))))

        onView(withId(R.id.iv_clearance))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }
}