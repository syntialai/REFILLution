package com.refillution.core.payload.request

import com.google.gson.annotations.SerializedName

data class UpdateOrderRequest(

    @SerializedName("status")
    var status: String? = null
)
