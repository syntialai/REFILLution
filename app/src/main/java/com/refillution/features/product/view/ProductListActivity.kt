package com.refillution.features.product.view

import android.view.LayoutInflater
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.refillution.base.view.BaseActivity
import com.refillution.core.data.local.ProductData
import com.refillution.databinding.ProductListActivityBinding
import com.refillution.features.product.adapter.ProductListAdapter
import com.refillution.util.router.Router

class ProductListActivity : BaseActivity<ProductListActivityBinding>() {

  companion object {
    private const val COLUMN_SPAN_COUNT = 2
  }

  private val productListAdapter by lazy {
    ProductListAdapter(::goToProductDetail)
  }

  override val viewBindingInflater: (LayoutInflater) -> ProductListActivityBinding
    get() = ProductListActivityBinding::inflate

  override fun setupViews() {
    setupToolbar(viewBinding.tProductList)
    setupRecyclerView()
  }

  override fun setupObserver() {
    super.setupObserver()

    productListAdapter.submitList(ProductData.getProducts())
  }

  private fun setupRecyclerView() {
    with(viewBinding.rvProductList) {
      layoutManager = StaggeredGridLayoutManager(COLUMN_SPAN_COUNT,
          StaggeredGridLayoutManager.VERTICAL)
      adapter = productListAdapter
      setHasFixedSize(false)
    }
  }

  private fun goToProductDetail(id: String) {
    Router.goToProductDetail(this, id)
  }
}