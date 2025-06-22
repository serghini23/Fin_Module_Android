package com.example.emtyapp.data.Repository

import com.example.emtyapp.data.Entities.Product
import com.example.emtyapp.data.remote.ProductApiService
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val apiService: ProductApiService
) {
    suspend fun getProducts(): List<Product> {
        return apiService.getProducts()
    }
}
