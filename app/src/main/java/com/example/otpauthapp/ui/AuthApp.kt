package com.example.otpauthapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.otpauthapp.viewModel.AuthViewModel
import com.example.otpauthapp.viewModel.Screen

@Composable
fun AuthApp(viewModel: AuthViewModel) {
    val state by viewModel.state.collectAsState()

    when (state.screen) {
        Screen.LOGIN -> LoginScreen(viewModel, state.errorMessage)
        Screen.OTP -> OtpScreen(viewModel, email = state.email,state.errorMessage)
        Screen.SESSION -> SessionScreen(
            startTime = state.sessionStartTime!!,
            onLogout = { viewModel.logout() }
        )
    }
}