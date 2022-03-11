package com.refillution.features.order.view

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.refillution.R
import com.refillution.base.view.BaseActivity
import com.refillution.databinding.OrderConfirmedActivityBinding
import com.refillution.core.payload.response.OrderResponse
import com.refillution.features.order.viewmodel.OrderConfirmedViewModel
import com.refillution.util.router.Router
import com.refillution.util.view.ViewUtils.loadImage
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderConfirmedActivity : BaseActivity<OrderConfirmedActivityBinding>() {

  private val viewModel: OrderConfirmedViewModel by viewModel()

  override val viewBindingInflater: (LayoutInflater) -> OrderConfirmedActivityBinding
    get() = OrderConfirmedActivityBinding::inflate

  override fun setupViews() {
    setupToolbar(viewBinding.tOrderConfirmed)
  }

  override fun setupToolbar(toolbar: Toolbar) {
    super.setupToolbar(toolbar)
    toolbar.setNavigationOnClickListener {
      Router.goToMain(this)
      finish()
    }
  }

  override fun setupObserver() {
    super.setupObserver()

    viewModel.getOrder(intent?.getStringExtra(Router.PARAM_ORDER_ID).orEmpty())

    viewModel.order.observe(this, { response ->
      updateOrderIdAndQRCode(response)
      updateDeliveryStatus()
    })

    viewModel.completeOrder.observe(this, {
      if (it) {
        Router.goToOrderCompleted(this)
        finish()
      }
    })
  }

  @SuppressLint("SetTextI18n")
  private fun updateOrderIdAndQRCode(order: OrderResponse) {
    viewBinding.tvOrderId.text = "Order #${order.id}"
    viewBinding.ivQrCode.loadImage(this, generateQRCode(order))
  }

  private fun updateDeliveryStatus() {
    lifecycleScope.launchWhenResumed {
      delay(5000L)
      viewBinding.tvDeliveryInfo.text = "Kurir sudah sampai."

      viewModel.completeOrder()
    }
  }

  private fun generateQRCode(orderResponse: OrderResponse? = null): Bitmap? {
    val qrgEncoder = QRGEncoder(orderResponse.toString(), null, QRGContents.Type.TEXT,
        getDimenSize(R.dimen.dp_200))
    return try {
      qrgEncoder.bitmap
    } catch (throwable: Throwable) {
      Log.v("QR CODE", throwable.toString())
      null
    }
  }
}