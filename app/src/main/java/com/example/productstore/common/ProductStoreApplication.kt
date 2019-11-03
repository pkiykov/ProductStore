package com.example.productstore.common

import android.app.Application
import com.example.productstore.common.dependencyinjection.CompositionRoot

class ProductStoreApplication : Application() {

    val compositionRoot: CompositionRoot by lazy {
        CompositionRoot(this)
    }

}