package com.refillution.util.view

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Currency
import java.util.Date
import java.util.Locale

object FormatUtils {

  fun Double.toIndonesiaCurrencyFormat(): String = NumberFormat.getCurrencyInstance().apply {
    maximumFractionDigits = 0
    currency = Currency.getInstance(Locale("in", "ID"))
  }.format(this)

  fun Int.toUnitString(unit: String, maxUnit: String) = if (shouldUseMaxUnit(this)) {
    "${this / 1000.0} $maxUnit"
  } else {
    "$this $unit"
  }

  fun Date?.toDateString(pattern: String = "yyyy-MM-dd"): String {
    return this?.let {
      SimpleDateFormat(pattern, Locale.ENGLISH).format(it)
    }.orEmpty()
  }

  private fun shouldUseMaxUnit(measure: Int) = measure >= 1000
}