package com.example.twg_android_test.search.ui.result

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twg_android_test.R
import com.example.twg_android_test.search.ui.detail.ProductDetailActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchResultActivity : AppCompatActivity() {

    companion object {
        const val FLAG_KEY_WORD = "keyWord"
    }

    private val viewModel: SearchResultViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchResultAdapter

    private var keyWord: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        keyWord = intent.getStringExtra(FLAG_KEY_WORD) ?: ""

        recyclerView = findViewById(R.id.recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        adapter = SearchResultAdapter()
        recyclerView.adapter = adapter

        adapter.onItemClick = { product ->
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra(ProductDetailActivity.EXTRA_PRODUCT, product)
            startActivity(intent)
        }

        recyclerView.addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                viewModel.loadMore(keyWord)
            }
        })

        lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                adapter.submitData(state.products)
                adapter.setLoadState(state.loadState)
            }
        }

        viewModel.refreshSearch(keyWord)

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