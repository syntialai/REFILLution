package com.refillution.core.payload.request

import com.google.gson.annotations.SerializedName

data class CreateOrderRequest(

    @SerializedName("amount")
    var amount: Int? = null,

    @SerializedName("deliveryDate")
    var deliveryDate: String? = null,

    @SerializedName("deliveryTime")
    var deliveryTime: String? = null,

    @SerializedName("productId")
    var productId: String? = null,

    @SerializedName("quantity")
    var quantity: Int? = null,

    @SerializedName("productPrice")
    var productPrice: Double? = null
)