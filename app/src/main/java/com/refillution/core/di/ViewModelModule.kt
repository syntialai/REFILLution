package com.refillution.core.di

import com.refillution.features.checkout.viewmodel.CheckoutViewModel
import com.refillution.features.order.viewmodel.OrderConfirmedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule =  module {
  viewModel { CheckoutViewModel(get()) }
  viewModel { OrderConfirmedViewModel(get()) }
}
