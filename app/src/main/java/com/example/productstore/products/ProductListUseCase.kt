package com.example.productstore.products

import com.example.productstore.db.ProductDatabaseUseCase
import com.example.productstore.networking.ProductListResponseSchema
import com.example.productstore.networking.ProductStoreApi
import org.jetbrains.anko.AnkoAsyncContext
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ProductListUseCase(
    private val productStoreApi: ProductStoreApi,
    private val databaseUseCase: ProductDatabaseUseCase
) {

    fun fetchProductList(onResult: (isSuccess: Boolean, response: List<Product>?) -> Unit) {
        doAsync {
            try {
                // GET product list
                val response = productStoreApi.fetchProductList()?.execute()
                if (response != null && response.isSuccessful) {
                    val productList = convertProductList(response.body()!!)
                    databaseUseCase.storeProductList(productList)
                    onSuccess(onResult, productList)
                } else {
                    onFailure(onResult)
                }
            } catch (e: Exception) { // TODO: differentiate exceptions and add proper error handling
                //try load from db
                try {
                    val productList = databaseUseCase.fetchProductList()
                    if (productList.isNotEmpty()) {
                        onSuccess(onResult, productList)
                    } else {
                        // no cached products
                        onFailure(onResult)
                    }
                } catch (e: Exception) {
                    onFailure(onResult)
                }
            }
        }
    }

    private fun convertProductList(responseProductList: ProductListResponseSchema): List<Product> {
        val products = mutableListOf<Product>()
        responseProductList.list.forEach {
            products.add(
                Product(
                    it.id,
                    it.name ?: "",
                    it.price?.toInt() ?: 0,
                    it.imageUrl ?: ""
                )
            )
        }
        return products
    }

}

private fun <T> AnkoAsyncContext<T>.onSuccess(
    onResult: (isSuccess: Boolean, response: List<Product>?) -> Unit,
    productList: List<Product>
) {
    uiThread { onResult(true, productList) }
}

private fun <T> AnkoAsyncContext<T>.onFailure(onResult: (isSuccess: Boolean, response: List<Product>?) -> Unit) {
    uiThread { onResult(false, null) }
}