package com.refillution.core.data.local

import com.refillution.core.model.Product
import com.refillution.core.model.ProductDetail

object ProductData {

  fun getProductById(id: String): ProductDetail? {
    return getProductDetails().firstOrNull { it.id == id }
  }

  fun getProducts(): List<Product> {
    return listOf(
        Product("0631ee41", "Dove Body Wash", 10000.0,
        "https://www.static-src.com/wcsstore/Indraprastha/images/catalog/full/dove_dove-deeply-nourishing-body-wash-pump-550ml_full04.jpg"),
        Product("dfdd27e1", "Daia Violet Deterjen", 20000.0,
            "https://www.static-src.com/wcsstore/Indraprastha/images/catalog/full//98/MTA-3790429/daia_detergent-daia-1-8kg-bks-purple_full02.jpg"),
        Product("204de14a", "Head & Shoulders Shampoo Lemon Fresh", 20000.0,
            "https://www.static-src.com/wcsstore/Indraprastha/images/catalog/full//95/MTA-26084281/head_-_shoulders_head_-_shoulders_shampoo_anti_ketombe_lemon_fresh_850ml_full01_gd633cjg.jpg")
    )
  }

  private fun getProductDetails(): List<ProductDetail> {
    return listOf(
        ProductDetail(
            id = "0631ee41",
            name = "Dove Body Wash",
            price = 10000.0,
            image = "https://www.static-src.com/wcsstore/Indraprastha/images/catalog/full/dove_dove-deeply-nourishing-body-wash-pump-550ml_full04.jpg",
            brand = "Dove",
            category = "Kesehatan dan Kecantikan",
            unitType = "Volume",
            unit = "ml",
            maxUnit = "liter",
            measurements = mapOf(
                250 to 10000.0,
                500 to 20000.0,
                1000 to 40000.0
            )
        ),
        ProductDetail(
            id = "dfdd27e1",
            name = "Daia Violet Deterjen",
            price = 20000.0,
            image = "https://www.static-src.com/wcsstore/Indraprastha/images/catalog/full//98/MTA-3790429/daia_detergent-daia-1-8kg-bks-purple_full02.jpg",
            brand = "Daia",
            category = "Kebutuhan rumah tangga",
            unitType = "Berat",
            unit = "gram",
            maxUnit = "kg",
            measurements = mapOf(
                500 to 20000.0,
                1000 to 34000.0
            )
        ),
        ProductDetail(
            id = "204de14a",
            name = "Head & Shoulders Shampoo Lemon Fresh",
            price = 20000.0,
            image = "https://www.static-src.com/wcsstore/Indraprastha/images/catalog/full//95/MTA-26084281/head_-_shoulders_head_-_shoulders_shampoo_anti_ketombe_lemon_fresh_850ml_full01_gd633cjg.jpg",
            brand = "Head & Shoulders",
            category = "Kebutuhan rumah tangga",
            unitType = "Volume",
            unit = "ml",
            maxUnit = "liter",
            measurements = mapOf(
                250 to 20000.0,
                500 to 35000.0,
                1000 to 68000.0
            )
        )
    )
  }
}