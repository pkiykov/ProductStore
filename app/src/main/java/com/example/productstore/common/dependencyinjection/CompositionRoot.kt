package com.example.productstore.common.dependencyinjection

import android.content.Context
import com.example.productstore.common.Constants.Companion.BASE_URL
import com.example.productstore.db.ProductDbQueries
import com.example.productstore.networking.ProductStoreApi
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CompositionRoot(private val context: Context) {

    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .create()

    private val retrofit: Retrofit by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getProductStoreApi(): ProductStoreApi = retrofit.create(ProductStoreApi::class.java)

    val productDbQueries: ProductDbQueries by lazy { ProductDbQueries(getContext()) }

    private fun getContext() = context

}