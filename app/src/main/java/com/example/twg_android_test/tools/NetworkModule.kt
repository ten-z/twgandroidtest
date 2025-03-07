package com.example.twg_android_test.tools

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    private var xTwgToken: String? = null

    fun setXTwgToken(token: String) {
        xTwgToken = token
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(Interceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .addHeader("Ocp-Apim-Subscription-Key", Constants.SUBSCRIPTION_KEY)
                xTwgToken?.let { token ->
                    requestBuilder.addHeader("X-TWG-Token", token)
                }
                chain.proceed(requestBuilder.build())
            })
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.HTTP_URL_ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val warehouseService: TWGService by lazy {
        retrofit.create(TWGService::class.java)
    }
}