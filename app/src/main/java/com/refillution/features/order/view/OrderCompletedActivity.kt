package com.refillution.features.order.view

import android.animation.Animator
import android.view.LayoutInflater
import com.refillution.base.view.BaseActivity
import com.refillution.databinding.OrderCompletedActivityBinding
import com.refillution.util.router.Router
import com.refillution.util.view.ViewUtils.remove
import com.refillution.util.view.ViewUtils.show

class OrderCompletedActivity : BaseActivity<OrderCompletedActivityBinding>() {

  override val viewBindingInflater: (LayoutInflater) -> OrderCompletedActivityBinding
    get() = OrderCompletedActivityBinding::inflate

  override fun setupViews() {
    startAnimation()
    setupListener()
  }

  override fun onBackPressed() {
    Router.goToMain(this)
    finish()
  }

  private fun setupListener() {
    viewBinding.bBack.setOnClickListener {
      onBackPressed()
    }
  }

  private fun startAnimation() {
    with(viewBinding.lavOrderCompleted) {
      addAnimatorListener(object : Animator.AnimatorListener {

        override fun onAnimationStart(animation: Animator?) {
          // No implementation needed
        }

        override fun onAnimationEnd(animation: Animator?) {
          remove()
          viewBinding.gOrderCompleted.show()
        }

        override fun onAnimationCancel(animation: Animator?) {
          // No implementation needed
        }

        override fun onAnimationRepeat(animation: Animator?) {
          // No implementation needed
        }
      })

      playAnimation()
    }
  }
}