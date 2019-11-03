package com.example.productstore.ui.productDetailed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.productstore.BR
import com.example.productstore.common.ProductStoreApplication
import com.example.productstore.common.dependencyinjection.ControllerCompositionRoot
import com.example.productstore.databinding.FragmentProductDetailedBinding
import kotlinx.android.synthetic.main.view_product_list_item.*
import org.jetbrains.anko.longToast

// TODO: create base fragment and extract some common logic there
class ProductDetailedFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentProductDetailedBinding
    private val controllerCompositionRoot: ControllerCompositionRoot by lazy {
        ControllerCompositionRoot((requireActivity().application as ProductStoreApplication).compositionRoot)
    }
    private val viewModel by viewModels<ProductDetailedViewModel> {
        controllerCompositionRoot.productDetailedViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentProductDetailedBinding.inflate(inflater, container, false).apply {
            viewmodel = this@ProductDetailedFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productId = arguments?.let { ProductDetailedFragmentArgs.fromBundle(it).id }
        viewModel.fetchProductList(productId)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.productDetailedLiveData.observe(viewLifecycleOwner, Observer {
            viewDataBinding.setVariable(BR.product, it)
            viewDataBinding.executePendingBindings()

            Glide.with(this).load(it.imageUrl).into(product_image)
        })
        viewModel.toastMessage.observe(viewLifecycleOwner, Observer {
            activity?.longToast(it)
        })
    }
}