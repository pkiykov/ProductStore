package com.example.productstore.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductStoreApi {

    @GET("cart/list")
    fun fetchProductList(): Call<ProductListResponseSchema>?

    @GET("cart/{productId}/detail")
    fun fetchProductDetailed(@Path("productId") productId: String): Call<ProductDetailedResponseSchema>?
}