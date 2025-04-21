package com.example.fetchrewardexcercise.repository

import com.example.fetchrewardexcercise.data.model.Item
import com.example.fetchrewardexcercise.data.network.ApiService

/*
class ItemRepository(private val apiService: ApiService) {

    suspend fun getFilteredSortedGroupedItems() : Map<Int, List<Item>>{
        return apiService.fetchItems()
            .filter { !it.name.isNullOrBlank() }
            .sortedWith(compareBy({it.listId}, {it.name}))
            .groupBy { it.listId  }
    }
    suspend fun getItems(): List<Item>
}*/


interface ItemRepository {
    suspend fun getItems(): List<Item>
}