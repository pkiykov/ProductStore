package com.example.productstore.ui.productList

import androidx.lifecycle.MutableLiveData
import com.example.productstore.common.dependencyinjection.ControllerCompositionRoot
import com.example.productstore.products.Product
import com.example.productstore.ui.view.base.BaseViewModel

class ProductListViewModel(controllerCompositionRoot: ControllerCompositionRoot) :
    BaseViewModel(controllerCompositionRoot) {
    val productListLiveData = MutableLiveData<List<Product>>()

    fun fetchProductList() {
        dataLoading.value = true
        controllerCompositionRoot.productListUseCase
            .fetchProductList { isSuccess, productList ->
                dataLoading.value = false
                if (isSuccess) {
                    if (productList.isNullOrEmpty()) {
                        empty.value = true
                    } else {
                        productListLiveData.value = productList
                        empty.value = false
                    }
                } else {
                    empty.value = true
                }
            }
    }
}