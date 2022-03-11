package com.refillution.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.refillution.core.payload.ResponseWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException

abstract class BaseViewModel : ViewModel() {

  val state: LiveData<ResponseWrapper<*>>
    get() = _state

  private val _state = MutableLiveData<ResponseWrapper<*>>()

  protected fun <T> fetchData(doFetch: suspend () -> T, onSuccess: ((T) -> Unit)? = null) {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        val response = doFetch.invoke()
        updateState(ResponseWrapper.Success(response))
        onSuccess?.invoke(response)
      } catch (throwable: Throwable) {
        updateState(when (throwable) {
          is IOException -> ResponseWrapper.NetworkError
          is HttpException -> getErrorResponseWrapper(throwable)
          else -> ResponseWrapper.Error()
        })
      }
    }
  }

  private suspend fun <T> updateState(responseWrapper: ResponseWrapper<T>) {
    withContext(Dispatchers.Main) {
      _state.value = responseWrapper
    }
  }

  private fun getErrorResponseWrapper(exception: HttpException): ResponseWrapper.Error {
    return ResponseWrapper.Error(exception.code(), exception.message())
  }
}