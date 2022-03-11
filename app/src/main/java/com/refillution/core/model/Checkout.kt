package com.refillution.core.model

import com.refillution.util.view.FormatUtils.toIndonesiaCurrencyFormat

data class Checkout(

    var productId: String,

    var quantity: Int,

    var measure: Int,

    var price: Double,

    var deliveryTime: String
) {

  fun getTotal(): String {
    return (quantity * price).toIndonesiaCurrencyFormat()
  }
}
