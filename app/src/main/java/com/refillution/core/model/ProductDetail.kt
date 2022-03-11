package com.refillution.core.model

data class ProductDetail(

    var id: String,

    var name: String,

    var price: Double,

    var image: String,

    var brand: String,

    var category: String,

    var unitType: String,

    var unit: String,

    var maxUnit: String,

    var measurements: Map<Int, Double>
)
