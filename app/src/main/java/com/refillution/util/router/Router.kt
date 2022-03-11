package com.refillution.util.router

import android.content.Context
import android.content.Intent
import com.refillution.features.main.MainActivity
import com.refillution.features.order.view.OrderCompletedActivity
import com.refillution.features.order.view.OrderConfirmedActivity
import com.refillution.features.product.view.ProductDetailActivity
import com.refillution.features.product.view.ProductListActivity

object Router {

  const val PARAM_PRODUCT_ID = "PARAM_PRODUCT_ID"
  const val PARAM_ORDER_ID = "PARAM_ORDER_ID"

  fun goToMain(context: Context) {
    val intent = getIntent(context, MainActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    }
    context.startActivity(intent)
  }

  fun goToOrderConfirmed(context: Context, orderId: String) {
    val intent = getIntent(context, OrderConfirmedActivity::class.java).apply {
      putExtra(PARAM_ORDER_ID, orderId)
    }
    context.startActivity(intent)
  }

  fun goToOrderCompleted(context: Context) {
    context.startActivity(getIntent(context, OrderCompletedActivity::class.java))
  }

  fun goToProductList(context: Context) {
    context.startActivity(getIntent(context, ProductListActivity::class.java))
  }

  fun goToProductDetail(context: Context, productId: String) {
    val intent = getIntent(context, ProductDetailActivity::class.java).apply {
      putExtra(PARAM_PRODUCT_ID, productId)
    }
    context.startActivity(intent)
  }

  private fun getIntent(context: Context, clazz: Class<*>): Intent {
    return Intent(context, clazz)
  }
}