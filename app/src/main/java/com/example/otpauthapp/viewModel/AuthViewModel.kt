package com.example.otpauthapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otpauthapp.data.OtpManager
import com.example.otpauthapp.data.OtpResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

class AuthViewModel : ViewModel() {

    private val otpManager = OtpManager()

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state

    fun sendOtp(email: String) {
//        otpManager.generateOtp(email)
        val otp = otpManager.generateOtp(email)

        Timber.tag("Timber").d("OTP generated for email=%s, otp=%s", email,otp)
        _state.update {
            it.copy(
                screen = Screen.OTP,
                email = email,
                errorMessage = null
            )
        }
    }

    fun verifyOtp(inputOtp: String) {
        val email = state.value.email

        when (otpManager.validateOtp(email, inputOtp)) {
            OtpResult.Success -> {
                Timber.tag("Timber").i("OTP validation success for email=%s", email)
                _state.update {
                    it.copy(
                        screen = Screen.SESSION,
                        sessionStartTime = System.currentTimeMillis(),
                        errorMessage = null
                    )
                }
            }

            OtpResult.Invalid ->{
                Timber.tag("Timber").w("Invalid for email=%s", email)
                showError("Invalid OTP")
            }

            OtpResult.Expired -> {
                Timber.tag("Timber").w("OTP expired for email=%s", email)
                showError("OTP expired")
            }

            OtpResult.TooManyAttempts -> {
                Timber.tag("Timber").w("Too many attempts for email=%s", email)
                showError("Too many attempts")
            }

            OtpResult.NoOtp ->{
                Timber.tag("Timber").w("OTP validation failed for email=%s", email)
                showError("No OTP generated")
            }
        }
    }

    fun logout() {
        Timber.tag("Timber").i("User logged out")
        _state.update {
            AuthState() // reset everything
        }
    }

    private fun showError(message: String) {
        _state.update {
            it.copy(errorMessage = message)
        }
    }
}