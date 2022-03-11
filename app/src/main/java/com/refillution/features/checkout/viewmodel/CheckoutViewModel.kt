package com.refillution.features.checkout.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.refillution.base.viewmodel.BaseViewModel
import com.refillution.core.data.service.OrderService
import com.refillution.core.model.Checkout
import com.refillution.core.payload.request.CreateOrderRequest
import com.refillution.core.payload.request.UpdateOrderRequest
import com.refillution.core.payload.response.OrderResponse
import com.refillution.util.view.FormatUtils.toDateString
import java.util.Calendar
import kotlinx.coroutines.launch

class CheckoutViewModel(private val orderService: OrderService): BaseViewModel() {

  val orderId: LiveData<String>
    get() = _orderId

  private var _orderId: MutableLiveData<String> = MutableLiveData()

  fun createOrder(checkout: Checkout?) {
    val createOrderRequest = constructCreateOrderRequest(checkout)

    fetchData(suspend {
      orderService.createOrder(createOrderRequest)
    }, { orderResponse ->
      updateOrder(orderResponse)
    })
  }

  private fun updateOrder(orderResponse: OrderResponse) {
    val updateOrderRequest = UpdateOrderRequest("PAID")

    fetchData(suspend {
      orderService.updateOrder(orderResponse.id ?: 0, updateOrderRequest)
    }, { response ->
      setOrderId(response)
    })
  }

  private fun setOrderId(updatedOrderResponse: OrderResponse) {
    viewModelScope.launch {
      _orderId.value = updatedOrderResponse.id.toString()
    }
  }

  private fun constructCreateOrderRequest(checkout: Checkout?): CreateOrderRequest {
    return CreateOrderRequest(
        amount = checkout?.measure,
        deliveryDate = Calendar.getInstance().time.toDateString(),
        deliveryTime = checkout?.deliveryTime,
        productId = checkout?.productId,
        quantity = checkout?.quantity,
        productPrice = checkout?.price
    )
  }
}