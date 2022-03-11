package com.refillution.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.refillution.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

  private var _viewBinding: VB? = null
  protected val viewBinding get() = _viewBinding as VB

  private var lifecycleJob: Job? = null

  protected abstract val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    _viewBinding = viewBindingInflater.invoke(inflater, container, false)
    return viewBinding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupViews()
    setupObserver()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _viewBinding = null
  }

  abstract fun setupViews()

  abstract fun setupObserver()

  open fun showEmptyState(isEmpty: Boolean) {}

  protected fun showErrorState(message: String?, @StringRes defaultMessageId: Int) {
    showErrorSnackbar((message ?: getString(defaultMessageId)))
  }

  private fun showErrorSnackbar(message: String) {
    if (message.isNotBlank()) {
      view?.findViewById<View>(android.R.id.content)?.let { view ->
        getSnackbar(view, message).setBackgroundTint(
            getColor(requireContext(), R.color.red_light_1_F7A1A1)).setTextColor(
            getColor(requireContext(), R.color.black_high_DE000000)).show()
      }
    }
  }

  protected fun showSnackbar(message: String) {
    if (message.isNotBlank()) {
      view?.findViewById<View>(android.R.id.content)?.let { view ->
        getSnackbar(view, message).show()
      }
    }
  }

  private fun getSnackbar(view: View, message: String) = Snackbar.make(view, message,
      Snackbar.LENGTH_SHORT)

  protected fun launchLifecycleScope(block: suspend () -> Unit) {
    lifecycleJob?.cancel()
    lifecycleJob = lifecycleScope.launch {
      block.invoke()
    }
  }
}