package com.refillution.features.checkout.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.refillution.R
import com.refillution.core.data.local.ProductData
import com.refillution.databinding.CheckoutBottomSheetDialogFragmentBinding
import com.refillution.core.model.Checkout
import com.refillution.core.model.ProductDetail
import com.refillution.features.checkout.viewmodel.CheckoutViewModel
import com.refillution.util.router.Router
import com.refillution.util.view.FormatUtils.toIndonesiaCurrencyFormat
import com.refillution.util.view.ViewUtils.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutBottomSheetDialogFragment : BottomSheetDialogFragment() {

  companion object {
    fun newInstance(checkout: Checkout) = CheckoutBottomSheetDialogFragment().apply {
      this.checkout = checkout
    }
  }

  private val viewModel: CheckoutViewModel by viewModel()

  private var _viewBinding: CheckoutBottomSheetDialogFragmentBinding? = null
  private val viewBinding: CheckoutBottomSheetDialogFragmentBinding
  get() = _viewBinding!!

  private var checkout: Checkout? = null

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    return (super.onCreateDialog(savedInstanceState) as BottomSheetDialog).apply {
      behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View {
    _viewBinding = CheckoutBottomSheetDialogFragmentBinding.inflate(layoutInflater)
    return viewBinding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewBinding.bPay.setOnClickListener {
      viewModel.createOrder(checkout)
    }

    val productDetail = ProductData.getProductById(checkout?.productId.orEmpty())
    productDetail?.let {
      setProductInfo(it, checkout)
      setDeliveryEstimation(checkout?.deliveryTime.orEmpty())
      setTotal(checkout?.getTotal().orEmpty())
    }

    setupObserver()
  }

  private fun setupObserver() {
    viewModel.orderId.observe(viewLifecycleOwner, { orderId ->
      onCreateOrderFinished(orderId)
    })
  }

  private fun setDeliveryEstimation(deliveryTime: String) {
    viewBinding.tvDeliveryEstimation.text = context?.getString(R.string.deliver_within_time,
        deliveryTime)
  }

  private fun setProductInfo(productDetail: ProductDetail, checkout: Checkout?) {
    with(viewBinding) {
      ivCheckoutProduct.loadImage(root.context, productDetail.image)

      tvProductName.text = productDetail.name

      lavtvPrice.setLabel(checkout?.price?.toIndonesiaCurrencyFormat().orEmpty())
      lavtvPrice.setValue("/ produk")

      tvProductVolume.text = "${productDetail.unitType}: ${checkout?.measure}"
      tvProductQuantity.text = "${context?.getString(R.string.quantity)} ${checkout?.quantity ?: 1}"
    }
  }

  private fun setTotal(total: String) {
    viewBinding.tvTotal.text = total
  }

  private fun onCreateOrderFinished(orderId: String) {
    activity?.let {
      Router.goToOrderConfirmed(it, orderId)
      dismiss()
      it.finish()
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _viewBinding = null
  }
}