package com.refillution.core.di

import com.refillution.core.data.service.OrderService
import java.util.concurrent.TimeUnit
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_API_URL = "https://refillution-api.herokuapp.com/"

val networkModule = module {

  fun provideRetrofit(): Retrofit {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
      setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    val interceptor = Interceptor { chain ->
      val request = chain.request()

//      val newUrl = request.url.newBuilder().addQueryParameter(API_KEY_QUERY,
//          BuildConfig.GITHUB_API_KEY).build()
      val requestBuilder = request.newBuilder()
          // .url(newUrl)

      chain.proceed(requestBuilder.build())
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(interceptor)
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .build()

    return Retrofit.Builder().baseUrl(BASE_API_URL).addConverterFactory(
        GsonConverterFactory.create()).client(okHttpClient).build()
  }

  single { provideRetrofit() }
}

val serviceModule = module {

  fun provideOrderService(retrofit: Retrofit): OrderService {
    return retrofit.create(OrderService::class.java)
  }

  single { provideOrderService(get()) }
}