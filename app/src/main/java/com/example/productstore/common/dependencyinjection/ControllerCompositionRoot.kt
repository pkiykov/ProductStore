package com.example.productstore.common.dependencyinjection

import androidx.lifecycle.ViewModelProvider
import com.example.productstore.db.ProductDatabaseUseCase
import com.example.productstore.products.ProductDetailedUseCase
import com.example.productstore.products.ProductListUseCase
import com.example.productstore.ui.productDetailed.ProductDetailedViewModelFactory
import com.example.productstore.ui.productList.ProductListViewModelFactory

class ControllerCompositionRoot(private val compositionRoot: CompositionRoot) {

    val productListViewModelFactory: ViewModelProvider.Factory by lazy {
        ProductListViewModelFactory(this)
    }

    val productDetailedViewModelFactory: ViewModelProvider.Factory by lazy {
        ProductDetailedViewModelFactory(this)
    }

    private val productStoreApi = compositionRoot.getProductStoreApi()

    val productDetailedUseCase: ProductDetailedUseCase by lazy {
        ProductDetailedUseCase(
            productStoreApi,
            productDatabaseUseCase
        )
    }

    val productListUseCase: ProductListUseCase by lazy {
        ProductListUseCase(
            productStoreApi,
            productDatabaseUseCase
        )
    }

    private val productDatabaseUseCase: ProductDatabaseUseCase by lazy {
        ProductDatabaseUseCase(
            compositionRoot.productDbQueries
        )
    }
}