package com.example.emtyapp.data.Entities

data class Order(
    val id: Int,
    val items: List<Product>,
    val timestamp: Long
)