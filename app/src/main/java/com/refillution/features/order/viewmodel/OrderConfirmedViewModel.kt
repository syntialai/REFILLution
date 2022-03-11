package com.refillution.features.order.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.refillution.base.viewmodel.BaseViewModel
import com.refillution.core.data.service.OrderService
import com.refillution.core.payload.request.UpdateOrderRequest
import com.refillution.core.payload.response.OrderResponse
import kotlinx.coroutines.launch

class OrderConfirmedViewModel(private val orderService: OrderService) : BaseViewModel() {

  val order: LiveData<OrderResponse>
    get() = _order

  val completeOrder: LiveData<Boolean>
    get() = _completeOrder

  private var _order: MutableLiveData<OrderResponse> = MutableLiveData()

  private var _completeOrder: MutableLiveData<Boolean> = MutableLiveData()

  fun getOrder(orderId: String) {
    fetchData(suspend {
      val id = orderId.toInt()
      orderService.getOrderById(id)
    }, { orderResponse ->
      setOrder(orderResponse)
    })
  }

  fun completeOrder() {
    val updateOrderRequest = UpdateOrderRequest("DONE")
    val id = _order.value?.id ?: return

    fetchData(suspend {
      orderService.updateOrder(id, updateOrderRequest)
    }, {
      setOrderCompleted()
    })
  }

  private fun setOrder(orderResponse: OrderResponse) {
    viewModelScope.launch {
      _order.value = orderResponse
    }
  }

  private fun setOrderCompleted() {
    viewModelScope.launch {
      _completeOrder.value = true
    }
  }
}