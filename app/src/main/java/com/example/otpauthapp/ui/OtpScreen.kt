package com.example.otpauthapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.otpauthapp.viewModel.AuthViewModel

@Composable
fun OtpScreen(
    viewModel: AuthViewModel,
    email: String,
    errorMessage: String?
) {
    var otp by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Enter OTP", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = otp,
            onValueChange = { otp = it },
            label = { Text("6-digit OTP") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.verifyOtp(otp) },
            enabled = otp.length == 6
        ) {
            Text("Verify OTP")
        }

        Button(
            onClick = { viewModel.sendOtp(email) },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Resend OTP")
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(12.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}