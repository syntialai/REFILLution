package com.refillution.core.payload

sealed class ResponseWrapper<out T> {

  data class Success<out T>(val data: T) : ResponseWrapper<T>()

  data class Error(val code: Int? = null, val message: String? = null) : ResponseWrapper<Nothing>()

  object NetworkError : ResponseWrapper<Nothing>()
}