package com.example.twg_android_test.search.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twg_android_test.search.data.Product
import com.example.twg_android_test.search.data.RemoteDataSource
import com.example.twg_android_test.search.data.SearchResultRepository
import com.example.twg_android_test.tools.NetworkModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class SearchUiState(
    val products: List<Product> = emptyList(),
    val loadState: Int = SearchResultAdapter.LOADING_COMPLETE,
    val totalItems: Int = 0
)

class SearchResultViewModel(
    private val repository: SearchResultRepository = SearchResultRepository(
        RemoteDataSource(NetworkModule.warehouseService)
    )
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var currentIndex = 0
    private val itemsPerPage = 20

    fun refreshSearch(keyword: String) {
        currentIndex = 0
        _uiState.value = SearchUiState(
            products = emptyList(), loadState = SearchResultAdapter.LOADING_COMPLETE, totalItems = 0
        )
        loadData(keyword)
    }

    fun loadMore(keyword: String) {
        if (_uiState.value.loadState == SearchResultAdapter.LOADING) {
            return
        }
        if (_uiState.value.products.size < _uiState.value.totalItems) {
            _uiState.update { it.copy(loadState = SearchResultAdapter.LOADING) }
            loadData(keyword)
        } else {
            _uiState.update { it.copy(loadState = SearchResultAdapter.LOADING_END) }
        }
    }

    private fun loadData(keyword: String) {

        viewModelScope.launch {
            repository.getSearchResult(keyword, currentIndex, itemsPerPage).catch {
                    _uiState.update { it.copy(loadState = SearchResultAdapter.LOADING_COMPLETE) }
                }.collect { searchResult ->
                    val total = searchResult.total ?: 0
                    currentIndex += itemsPerPage

                    val newProducts = searchResult.products ?: emptyList()
                    _uiState.update { currentState ->
                        currentState.copy(
                            products = currentState.products + newProducts,
                            loadState = SearchResultAdapter.LOADING_COMPLETE,
                            totalItems = total
                        )
                    }
                }
        }
    }
}