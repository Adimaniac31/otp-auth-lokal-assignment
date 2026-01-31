package com.example.otpauthapp.viewModel

enum class Screen {
    LOGIN,
    OTP,
    SESSION
}

data class AuthState(
    val screen: Screen = Screen.LOGIN,
    val email: String = "",
    val errorMessage: String? = null,
    val sessionStartTime: Long? = null
)