package com.example.productstore.ui.view.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.productstore.common.dependencyinjection.ControllerCompositionRoot

abstract class BaseViewModel(protected val controllerCompositionRoot: ControllerCompositionRoot) : ViewModel() {

    val empty = MutableLiveData<Boolean>().apply { value = false }

    val dataLoading = MutableLiveData<Boolean>().apply { value = false }

    val toastMessage = MutableLiveData<String>()

}