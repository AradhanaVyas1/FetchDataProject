package com.example.fetchrewardexcercise.data.network

import com.example.fetchrewardexcercise.data.model.Item
import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json")
    suspend fun fetchItems(): List<Item>
}