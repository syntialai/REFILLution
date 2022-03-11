package com.refillution.util.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import com.refillution.R
import com.refillution.databinding.WidgetLabelAndValueTextViewLayoutBinding

class LabelAndValueTextView constructor(context: Context, attrs: AttributeSet) :
    LinearLayout(context, attrs) {

  private lateinit var binding: WidgetLabelAndValueTextViewLayoutBinding

  private val styledAttributes = context.obtainStyledAttributes(attrs,
      R.styleable.LabelAndValueTextView)

  init {
    initBinding()
    styledAttributes.apply {
      setSize(getInt(R.styleable.LabelAndValueTextView_size, 0))
      setLabel(getString(R.styleable.LabelAndValueTextView_label))
      setValueColor(getColor(R.styleable.LabelAndValueTextView_valueColor,
          context.resources.getColor(R.color.blue_0095DA)))
      recycle()
    }
  }

  private fun setSize(size: Int) {
    with(binding) {
      if (size == 1) {
        tvValue.setTextAppearance(R.style.TextAppearance_Refillution_Body2)
      }
    }
  }

  fun setValueColor(@ColorInt colorId: Int) {
    binding.tvValue.setTextColor(colorId)
  }

  fun setValue(value: String?, onClickListener: (() -> Unit)? = null) {
    with(binding.tvValue) {
      text = value
      setOnClickListener {
        onClickListener?.invoke()
      }
    }
  }

  fun setLabel(label: String?) {
    binding.tvLabel.text = label
  }

  private fun initBinding() {
    val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    binding = WidgetLabelAndValueTextViewLayoutBinding.inflate(layoutInflater, this, true)
  }
}