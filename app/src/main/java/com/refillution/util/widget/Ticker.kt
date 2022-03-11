package com.refillution.util.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.card.MaterialCardView
import com.refillution.R
import com.refillution.databinding.WidgetTickerLayoutBinding

class Ticker constructor(context: Context, attrs: AttributeSet) :
  MaterialCardView(context, attrs) {

  private lateinit var binding: WidgetTickerLayoutBinding

  private val styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.Ticker)

  init {
    initBinding()
    styledAttributes.apply {
      setDescription(getString(R.styleable.Ticker_description))
      recycle()
    }
  }

  private fun setDescription(description: String?) {
    binding.tvTickerDescription.text = description
  }

  private fun initBinding() {
    val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    binding = WidgetTickerLayoutBinding.inflate(layoutInflater, this, true)
  }
}