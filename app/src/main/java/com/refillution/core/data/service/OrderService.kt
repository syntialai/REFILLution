package com.refillution.core.data.service

import com.refillution.core.payload.request.CreateOrderRequest
import com.refillution.core.payload.request.UpdateOrderRequest
import com.refillution.core.payload.response.OrderResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderService {

  @GET("order/{id}")
  suspend fun getOrderById(@Path("id") id: Int): OrderResponse

  @POST("order")
  suspend fun createOrder(@Body request: CreateOrderRequest): OrderResponse

  @PATCH("order/{id}")
  suspend fun updateOrder(@Path("id") id: Int, @Body request: UpdateOrderRequest): OrderResponse
}