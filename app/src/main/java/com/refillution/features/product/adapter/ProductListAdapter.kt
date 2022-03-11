package com.refillution.features.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.refillution.base.adapter.BaseDiffCallback
import com.refillution.base.adapter.BaseViewHolder
import com.refillution.databinding.ItemProductListBinding
import com.refillution.features.product.adapter.ProductListAdapter.ViewHolder
import com.refillution.core.model.Product
import com.refillution.util.view.FormatUtils.toIndonesiaCurrencyFormat
import com.refillution.util.view.ViewUtils.loadImage

class ProductListAdapter(private val onClickListener: (String) -> Unit) :
    ListAdapter<Product, ViewHolder>(diffCallback) {

  companion object {
    val diffCallback = object : BaseDiffCallback<Product>() {
      override fun contentEquality(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val viewBinding = ItemProductListBinding.inflate(LayoutInflater.from(parent.context), parent,
        false)
    return ViewHolder(viewBinding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    getItem(position)?.let { model ->
      holder.bind(model)
    }
  }

  inner class ViewHolder(binding: ItemProductListBinding) :
      BaseViewHolder<Product, ItemProductListBinding>(binding) {

    override fun bind(data: Product) {
      with(viewBinding) {
        ivProduct.loadImage(root.context, data.image)

        tvProductName.text = data.name
        tvProductPrice.text = data.price.toIndonesiaCurrencyFormat()

        cvProductItem.setOnClickListener {
          onClickListener.invoke(data.id)
        }
      }
    }
  }
}