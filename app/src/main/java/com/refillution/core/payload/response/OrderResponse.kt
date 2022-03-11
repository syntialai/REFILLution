package com.refillution.core.payload.response

import com.google.gson.annotations.SerializedName

data class OrderResponse(

    @SerializedName("id")
    var id: Int? = null,

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

    @SerializedName("status")
    var status: String? = null,

    @SerializedName("productPrice")
    var productPrice: Double? = null,

    @SerializedName("price")
    var price: Double? = null
)