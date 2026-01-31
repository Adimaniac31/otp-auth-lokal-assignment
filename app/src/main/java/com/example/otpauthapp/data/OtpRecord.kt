package com.example.otpauthapp.data

data class OtpRecord(
    val otp: String,
    val expiresAt: Long,
    val attemptsLeft: Int
)
