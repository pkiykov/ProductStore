package com.example.productstore.products

import com.example.productstore.db.ProductDatabaseUseCase
import com.example.productstore.networking.ProductDetailedResponseSchema
import com.example.productstore.networking.ProductStoreApi
import org.jetbrains.anko.AnkoAsyncContext
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ProductDetailedUseCase(
    private val productStoreApi: ProductStoreApi,
    private val databaseUseCase: ProductDatabaseUseCase
) {

    fun fetchProductDetailed(
        productId: String,
        onResult: (isSuccess: Boolean, response: ProductDetailed?) -> Unit
    ) {
        doAsync {
            try {
                // GET product details
                val response = productStoreApi.fetchProductDetailed(productId)?.execute()
                if (response != null && response.isSuccessful) {
                    val productDetailed = convertProductDetailed(response.body()!!)
                    databaseUseCase.storeProductDetailed(productDetailed)
                    onSuccess(onResult, productDetailed)
                } else {
                    onFailure(onResult)
                }
            } catch (e: Exception) {// TODO: differentiate exceptions and add proper error handling
                try {
                    // try load from cache
                    val productDetailed = databaseUseCase.fetchProductDetailed(productId)
                    if (productDetailed != null) {
                        onSuccess(onResult, productDetailed)
                    } else {
                        onFailure(onResult)
                    }
                } catch (e: Exception) {
                    onFailure(onResult)
                }
            }
        }
    }

    private fun convertProductDetailed(productDetailedResponseSchema: ProductDetailedResponseSchema) =
        ProductDetailed(
            productDetailedResponseSchema.id,
            productDetailedResponseSchema.name ?: "",
            productDetailedResponseSchema.price ?: 0,
            productDetailedResponseSchema.imageUrl ?: "",
            productDetailedResponseSchema.description ?: ""
        )
}

private fun <T> AnkoAsyncContext<T>.onSuccess(
    onResult: (isSuccess: Boolean, response: ProductDetailed?) -> Unit,
    productDetailed: ProductDetailed
) {
    uiThread { onResult(true, productDetailed) }
}

private fun <T> AnkoAsyncContext<T>.onFailure(onResult: (isSuccess: Boolean, response: ProductDetailed?) -> Unit) {
    uiThread { onResult(false, null) }
}