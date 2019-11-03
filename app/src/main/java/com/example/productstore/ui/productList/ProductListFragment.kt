package com.example.productstore.ui.productList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.productstore.common.ProductStoreApplication
import com.example.productstore.common.dependencyinjection.ControllerCompositionRoot
import com.example.productstore.databinding.FragmentProductListBinding
import com.example.productstore.ui.view.adapter.ProductListAdapter
import kotlinx.android.synthetic.main.fragment_product_list.*
import org.jetbrains.anko.longToast

// TODO: create base fragment and extract some common logic there
class ProductListFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentProductListBinding
    private lateinit var adapter: ProductListAdapter
    private val controllerCompositionRoot: ControllerCompositionRoot by lazy {
        ControllerCompositionRoot((requireActivity().application as ProductStoreApplication).compositionRoot)
    }

    private val viewModel by viewModels<ProductListViewModel> {
        controllerCompositionRoot.productListViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentProductListBinding.inflate(inflater, container, false).apply {
            viewmodel = this@ProductListFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewmodel?.fetchProductList()

        setupAdapter()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.productListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.updateProductList(it)
        })

        viewModel.toastMessage.observe(viewLifecycleOwner, Observer {
            activity?.longToast(it)
        })
    }

    private fun setupAdapter() {
        adapter = ProductListAdapter()
        val layoutManager = GridLayoutManager(activity, 2)
        product_list_recycler_view.layoutManager = layoutManager
        product_list_recycler_view.adapter = adapter
    }
}