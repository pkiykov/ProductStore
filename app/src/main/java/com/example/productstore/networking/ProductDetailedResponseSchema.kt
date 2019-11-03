package com.example.productstore.networking

import com.google.gson.annotations.SerializedName

data class ProductDetailedResponseSchema(
    @SerializedName("product_id")
    val id: String, // must not be null
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("price")
    val price: Int? = 0,
    @SerializedName("image")
    val imageUrl: String? = "",
    @SerializedName("description")
    val description: String? = ""
)