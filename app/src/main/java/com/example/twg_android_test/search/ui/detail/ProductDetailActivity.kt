package com.example.twg_android_test.search.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twg_android_test.R
import com.example.twg_android_test.search.data.Product

class ProductDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PRODUCT = "extra_product"
    }

    private lateinit var rvGallery: RecyclerView
    private lateinit var tvProduct: TextView
    private lateinit var tvPrice: TextView
    private lateinit var ivClearance: ImageView
    private lateinit var tvBarcode: TextView
    private lateinit var tvProductDescription: TextView
    private lateinit var tvProductId: TextView
    private lateinit var tvSoldOnline: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        rvGallery = findViewById(R.id.rv_product_gallery)
        tvProduct = findViewById(R.id.tv_product)
        tvPrice = findViewById(R.id.tv_price)
        ivClearance = findViewById(R.id.iv_clearance)
        tvBarcode = findViewById(R.id.tv_barcode)
        tvProductDescription = findViewById(R.id.tv_product_description)
        tvProductId = findViewById(R.id.tv_product_id)
        tvSoldOnline = findViewById(R.id.tv_sold_online)

        val product: Product? =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(EXTRA_PRODUCT, Product::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra(EXTRA_PRODUCT)
            }
        product?.let { p ->
            val galleryAdapter = GalleryAdapter(p.detailimageUrls)
            rvGallery.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            rvGallery.adapter = galleryAdapter

            tvProduct.text = p.productName
            tvPrice.text = "$${p.price}"
            tvBarcode.text = p.productBarcode
            tvProductDescription.text = p.productDescription
            tvProductId.text = "Product ID: ${p.productId}"
            tvSoldOnline.text = "Sold Online: ${p.soldOnline}"

            ivClearance.visibility = if (p.promotionList.isNotEmpty()) View.VISIBLE else View.GONE
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
