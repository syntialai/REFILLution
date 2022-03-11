package com.refillution

import android.app.Application
import com.refillution.core.di.networkModule
import com.refillution.core.di.serviceModule
import com.refillution.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class RefillutionApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidLogger(Level.ERROR)
      androidContext(this@RefillutionApplication)
      modules(listOf(networkModule, serviceModule, viewModelModule))
    }
  }
}