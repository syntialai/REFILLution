package com.refillution.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.refillution.R

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

  private var _viewBinding: VB? = null
  protected val viewBinding get() = _viewBinding as VB

  protected abstract val viewBindingInflater: (LayoutInflater) -> VB

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _viewBinding = viewBindingInflater.invoke(layoutInflater)
    setContentView(viewBinding.root)
    setupViews()
    setupObserver()
  }

  override fun onDestroy() {
    super.onDestroy()
    _viewBinding = null
  }

  abstract fun setupViews()

  open fun setupObserver() {
  }

  protected open fun setupToolbar(toolbar: Toolbar) {
    with(toolbar) {
      setSupportActionBar(this)
      setNavigationOnClickListener {
        onBackPressed()
      }
    }
  }

  open fun showEmptyState(isEmpty: Boolean) {}

  open fun showLoadingState(isLoading: Boolean) {}

  open fun showErrorState(isError: Boolean, message: String?, @StringRes defaultMessageId: Int) {
    showErrorSnackbar((message ?: getString(defaultMessageId)))
  }

  private fun showErrorSnackbar(message: String) {
    if (message.isNotBlank()) {
      findViewById<View>(android.R.id.content)?.let { view ->
        getSnackbar(view, message).setBackgroundTint(
            getColor(R.color.red_light_1_F7A1A1)).setTextColor(
            getColor(R.color.black_high_DE000000)).show()
      }
    }
  }

  protected fun showSnackbar(message: String) {
    if (message.isNotBlank()) {
      findViewById<View>(android.R.id.content)?.let { view ->
        getSnackbar(view, message).show()
      }
    }
  }

  protected fun getDimenSize(@DimenRes dimenId: Int) = resources.getDimensionPixelSize(dimenId)

  private fun getSnackbar(view: View, message: String) = Snackbar.make(view, message,
      Snackbar.LENGTH_SHORT)
}