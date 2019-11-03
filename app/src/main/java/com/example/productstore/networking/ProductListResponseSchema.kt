package com.example.productstore.networking

import com.google.gson.annotations.SerializedName

data class ProductListResponseSchema(
    @SerializedName("products")
    val list: List<ProductResponseSchema>
)