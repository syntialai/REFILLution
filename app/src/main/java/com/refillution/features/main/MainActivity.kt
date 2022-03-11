package com.refillution.features.main

import android.view.LayoutInflater
import com.refillution.base.view.BaseActivity
import com.refillution.databinding.MainActivityBinding
import com.refillution.util.router.Router

class MainActivity : BaseActivity<MainActivityBinding>() {

  override val viewBindingInflater: (LayoutInflater) -> MainActivityBinding
    get() = MainActivityBinding::inflate

  override fun setupViews() {
    with(viewBinding) {
      ivMain.setOnClickListener {
        Router.goToProductList(this@MainActivity)
      }
    }
  }
}