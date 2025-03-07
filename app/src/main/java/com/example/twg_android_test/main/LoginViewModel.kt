package com.example.twg_android_test.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: LoginRepository = LoginRepository()
) : ViewModel() {
    private val _tokenState = MutableStateFlow<String?>(null)
    val tokenState: StateFlow<String?> = _tokenState

    fun login() {
        viewModelScope.launch {
            repository.loginAsGuest().collect { token ->
                _tokenState.value = token
            }
        }
    }
}