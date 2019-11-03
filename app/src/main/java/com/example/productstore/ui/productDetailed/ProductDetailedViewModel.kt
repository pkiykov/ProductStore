package com.example.productstore.ui.productDetailed

import androidx.lifecycle.MutableLiveData
import com.example.productstore.common.dependencyinjection.ControllerCompositionRoot
import com.example.productstore.products.ProductDetailed
import com.example.productstore.ui.view.base.BaseViewModel

class ProductDetailedViewModel(controllerCompositionRoot: ControllerCompositionRoot) : BaseViewModel(controllerCompositionRoot) {
    val productDetailedLiveData = MutableLiveData<ProductDetailed>()

    fun fetchProductList(productId: String?) {
        dataLoading.value = true
        if (productId == null) {
            empty.value = true
            return
        }
        controllerCompositionRoot.productDetailedUseCase.fetchProductDetailed(productId) { isSuccess, productDetailed ->
            dataLoading.value = false
            if (isSuccess) {
                if (productDetailed == null) {
                    empty.value = true
                } else {
                    productDetailedLiveData.value = productDetailed
                    empty.value = false
                }
            } else {
                empty.value = true
            }
        }
    }
}