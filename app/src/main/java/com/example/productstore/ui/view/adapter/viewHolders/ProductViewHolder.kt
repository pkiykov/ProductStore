package com.example.productstore.ui.view.adapter.viewHolders

import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productstore.BR
import com.example.productstore.R
import com.example.productstore.common.Constants.Companion.PRODUCT_ID
import com.example.productstore.products.Product
import kotlinx.android.synthetic.main.view_product_list_item.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class ProductViewHolder(private val dataBinding: ViewDataBinding) :
    RecyclerView.ViewHolder(dataBinding.root) {

    private val productImage: ImageView = itemView.product_image

    fun setup(productData: Product) {
        dataBinding.setVariable(BR.product, productData)
        dataBinding.executePendingBindings()

        Glide.with(itemView).load(productData.imageUrl).into(productImage)

        itemView.onClick {
            val bundle = bundleOf(PRODUCT_ID to productData.id)
            itemView.findNavController()
                .navigate(R.id.action_productListFragment_to_productDetailedFragment, bundle)
        }
    }
}