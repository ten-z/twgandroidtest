package com.example.twg_android_test.search.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    @SerializedName("productName")
    val productName: String,
    @SerializedName("priceInfo")
    private val priceInfo: PriceInfo,
    @SerializedName("imageGroups")
    private val imageGroups: List<ImageGroup>,
    @SerializedName("productImageUrl")
    val productImageUrl: String,
    @SerializedName("productId")
    val productId: String,
    @SerializedName("productBarcode")
    val productBarcode: String,
    @SerializedName("productDescription")
    val productDescription: String,
    @SerializedName("soldOnline")
    val soldOnline: String,
    @SerializedName("promotions")
    val promotions: List<Promotion>?
) : Parcelable {
    val price: Double
        get() = priceInfo.price

    val detailimageUrls: List<String>
        get() = imageGroups.firstOrNull()?.imageUrls ?: emptyList()

    val promotionList: List<Promotion>
        get() = promotions ?: emptyList()
}

@Parcelize
data class PriceInfo(
    @SerializedName("price")
    val price: Double
) : Parcelable

@Parcelize
data class ImageGroup(
    @SerializedName("imageUrls")
    val imageUrls: List<String>
) : Parcelable

@Parcelize
data class Promotion(
    @SerializedName("promotionId")
    val promotionId: String,
    @SerializedName("dealDescription")
    val dealDescription: String,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("isMarketClubExclusive")
    val isMarketClubExclusive: Boolean,
    @SerializedName("description")
    val description: String,
    @SerializedName("tags")
    val tags: List<String>?
) : Parcelable