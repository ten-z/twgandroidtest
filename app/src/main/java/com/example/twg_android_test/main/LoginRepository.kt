package com.example.twg_android_test.main

import com.example.twg_android_test.tools.NetworkModule
import com.example.twg_android_test.tools.TWGService
import kotlinx.coroutines.flow.flow

open class LoginRepository(
    private val service: TWGService = NetworkModule.warehouseService
) {
    open fun loginAsGuest() = flow {
        val response = service.loginAsGuest("Guest", "Android")
        if (response.isSuccessful) {
            val token = response.headers()["X-TWL-Token"]
            if (token != null) {
                emit(token)
            } else {
                emit(null)
            }
        } else {
            emit(null)
        }
    }
}
