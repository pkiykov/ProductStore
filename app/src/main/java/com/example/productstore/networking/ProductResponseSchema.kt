package com.example.productstore.networking

import com.google.gson.annotations.SerializedName

data class ProductResponseSchema(
    @SerializedName("product_id")
    val id: String, // must not be null
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("price")
    val price: String? = "",
    @SerializedName("image")
    val imageUrl: String? = ""
)