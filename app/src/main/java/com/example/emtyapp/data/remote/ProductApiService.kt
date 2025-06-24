package com.example.emtyapp.data.remote

import com.example.emtyapp.data.Entities.Product
import retrofit2.http.GET

interface ProductApiService {

    @GET("products")
    suspend fun getProducts(): List<Product>
}

