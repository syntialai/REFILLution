package com.refillution.features.product.view

import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.refillution.R
import com.refillution.base.view.BaseActivity
import com.refillution.core.data.local.ProductData
import com.refillution.databinding.ProductDetailActivityBinding
import com.refillution.features.checkout.view.CheckoutBottomSheetDialogFragment
import com.refillution.core.model.Checkout
import com.refillution.core.model.ProductDetail
import com.refillution.util.router.Router
import com.refillution.util.view.FormatUtils.toIndonesiaCurrencyFormat
import com.refillution.util.view.FormatUtils.toUnitString
import com.refillution.util.view.ViewUtils.loadImage
import com.refillution.util.view.ViewUtils.remove
import java.util.Calendar
import kotlinx.coroutines.delay

class ProductDetailActivity : BaseActivity<ProductDetailActivityBinding>() {

  private var productDetail: ProductDetail? = null

  override val viewBindingInflater: (LayoutInflater) -> ProductDetailActivityBinding
    get() = ProductDetailActivityBinding::inflate

  override fun setupViews() {
    setSupportActionBar(null)
    setupToolbar(viewBinding.tProductDetail)
    setupListener()
  }

  override fun setupObserver() {
    super.setupObserver()

    val productId = intent?.getStringExtra(Router.PARAM_PRODUCT_ID)
    productId?.let {
      productDetail = ProductData.getProductById(it)
    }

    productDetail?.let {
      setupProductDetailMainInfo()
      setupProductDescription()
      setupProductVariant(it)
      setupVolumeOrMassChips(it.measurements, it.unit, it.maxUnit)
      setupQuantityChips()
      setupDeliveryTimes()
    }
  }

  private fun setupListener() {
    with(viewBinding.fabCheckout) {
      setOnClickListener {
        shrink(object : ExtendedFloatingActionButton.OnChangedCallback() {

          override fun onShrunken(extendedFab: ExtendedFloatingActionButton?) {
            super.onShrunken(extendedFab)
            openCheckoutBottomSheet()
            lifecycleScope.launchWhenResumed {
              delay(1000)
              extend()
            }
          }
        })
      }
    }
  }

  private fun setupProductDetailMainInfo() {
    with(viewBinding) {
      ivProductDetail.loadImage(
          this@ProductDetailActivity,
          productDetail?.image
      )
      tvProductName.text = productDetail?.name
      tvProductPrice.text = productDetail?.price?.toIndonesiaCurrencyFormat()
    }
  }

  private fun updateProductPrice(price: Double) {
    viewBinding.tvProductPrice.text = price.toIndonesiaCurrencyFormat()
  }

  private fun setupProductVariant(productDetail: ProductDetail) {
    viewBinding.tvVolumeOrMassLabel.text = "${productDetail.unitType}:"
    setupVolumeOrMassChips(productDetail.measurements, productDetail.unit, productDetail.maxUnit)

  }

  private fun setupVolumeOrMassChips(
      measures: Map<Int, Double>, unit: String, maxUnit: String) {
    with(viewBinding.cgVolumeOrMass) {
      removeAllViews()
      measures.keys.forEachIndexed { index, measure ->
        addView(getProductVariantChip(
            measure, measure.toUnitString(unit, maxUnit), this, index == 0))
      }
      setOnCheckedChangeListener { _, checkedId ->
        measures[checkedId]?.let { price ->
          updateProductPrice(price)
        }
      }
    }
  }

  private fun setupQuantityChips() {
    with(viewBinding.cgQuantity) {
      removeAllViews()
      for (quantity in 1 .. 5) {
        addView(getProductVariantChip(quantity, quantity.toString(), this, quantity == 1))
      }
    }
  }

  private fun setupDeliveryTimes() {
    val calendar = Calendar.getInstance()
    val hour = 10 // calendar.get(Calendar.HOUR_OF_DAY)

    val deliveryTimes = (hour .. 17 step 2).map { time ->
      "$time.00 s.d ${time + 1}.00"
    }

    if (deliveryTimes.isEmpty()) {
      viewBinding.tvDeliveryTime.remove()
      viewBinding.cgDeliveryTime.remove()
      viewBinding.fabCheckout.isEnabled = false
    } else {
      setupDeliveryChips(deliveryTimes)
    }
  }

  private fun setupDeliveryChips(deliveryTimes: List<String>) {
    with(viewBinding.cgDeliveryTime) {
      removeAllViews()
      deliveryTimes.forEachIndexed { index, deliveryTime ->
        addView(getProductVariantChip(index, deliveryTime, this, index == 0))
      }
    }
  }

  private fun getProductVariantChip(index: Int, text: String, chipGroup: ChipGroup,
      shouldCheck: Boolean): Chip {
    val chipBinding = layoutInflater.inflate(R.layout.item_product_variant_chip, chipGroup,
        false) as Chip
    with(chipBinding) {
      this.id = index
      this.text = text
      this.isChecked = shouldCheck
    }
    return chipBinding
  }

  private fun setupProductDescription() {
    with(viewBinding) {
      lavtvMerk.setValue(productDetail?.brand)
      lavtvCategory.setValue(productDetail?.category)
    }
  }

  private fun openCheckoutBottomSheet() {
    val measure = viewBinding.cgVolumeOrMass.checkedChipId
    val checkedDeliveryTimeChip = viewBinding.cgDeliveryTime.findViewById<Chip>(
        viewBinding.cgDeliveryTime.checkedChipId)
    val checkout = Checkout(
        productDetail?.id.orEmpty(),
        viewBinding.cgQuantity.checkedChipId,
        measure,
        productDetail?.measurements?.get(measure) ?: 0.0,
        checkedDeliveryTimeChip.text.toString()
    )
    CheckoutBottomSheetDialogFragment.newInstance(checkout).show(
        supportFragmentManager, ProductDetailActivity::class.java.simpleName)
  }
}