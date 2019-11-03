package com.example.productstore.products

data class Product(val id: String, val name: String, val price: Int, val imageUrl: String, val uri: String? = null)