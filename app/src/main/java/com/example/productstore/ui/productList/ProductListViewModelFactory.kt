package com.example.productstore.ui.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.productstore.common.dependencyinjection.ControllerCompositionRoot

class ProductListViewModelFactory(private val controllerCompositionRoot: ControllerCompositionRoot) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return ProductListViewModel(controllerCompositionRoot) as T
    }

}