package com.example.productstore.products

data class ProductDetailed(
    val id: String,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val description: String
)