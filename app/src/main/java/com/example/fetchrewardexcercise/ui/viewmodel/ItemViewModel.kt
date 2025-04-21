package com.example.fetchrewardexcercise.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchrewardexcercise.data.model.Item
import com.example.fetchrewardexcercise.data.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ItemViewModel : ViewModel() {
    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items

    private val _filterQuery = MutableStateFlow("")
    val filterQuery: StateFlow<String> = _filterQuery

    private val _filterMode = MutableStateFlow(FilterMode.DEFAULT)
    val filterMode: StateFlow<FilterMode> = _filterMode

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.fetchItems()
                val filtered = response.filter { !it.name.isNullOrBlank() }
                _items.value = filtered
            } catch (e: Exception) {
                Log.e("ItemViewModel", "Error: ${e.message}")
            }
        }
    }

    fun updateFilter(query: String) {
        _filterQuery.value = query
    }

    fun updateFilterMode(mode: FilterMode) {
        _filterMode.value = mode
    }

    val filteredItems: StateFlow<List<Item>> = combine(_items, _filterQuery, _filterMode) { items, query, mode ->
        var result = items.filter { it.name!!.contains(query, ignoreCase = true) }
        result = when (mode) {
            FilterMode.DEFAULT -> result.sortedWith(compareBy({ it.listId }, { it.name }))
            FilterMode.SORT_BY_NUMBER -> result.sortedWith(compareBy({ it.listId }, { extractNumberFromName(it.name!!) }))
        }
        result
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private fun extractNumberFromName(name: String): Int {
        return Regex("\\d+").find(name)?.value?.toIntOrNull() ?: Int.MAX_VALUE
    }
}