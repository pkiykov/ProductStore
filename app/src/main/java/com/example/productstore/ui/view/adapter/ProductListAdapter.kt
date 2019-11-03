package com.example.productstore.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.productstore.databinding.ViewProductListItemBinding
import com.example.productstore.products.Product
import com.example.productstore.ui.view.adapter.viewHolders.ProductViewHolder

class ProductListAdapter : RecyclerView.Adapter<ProductViewHolder>() {
    var productList: List<Product> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = ViewProductListItemBinding.inflate(inflater, parent, false)
        return ProductViewHolder(dataBinding)
    }

    override fun getItemCount() = productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.setup(productList[position])
    }

    fun updateProductList(productList: List<Product>) {
        this.productList = productList
        notifyDataSetChanged()
    }
}