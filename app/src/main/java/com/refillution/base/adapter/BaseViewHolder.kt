package com.refillution.base.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T : Any, VB : ViewBinding>(protected val viewBinding: VB) :
    RecyclerView.ViewHolder(viewBinding.root) {

  abstract fun bind(data: T)
}