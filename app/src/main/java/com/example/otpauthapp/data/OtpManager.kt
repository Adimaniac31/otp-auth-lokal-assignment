package com.example.otpauthapp.data

import kotlin.random.Random

class OtpManager {

    private val otpStore = mutableMapOf<String, OtpRecord>()

    private val otpExpiryMillis = 60_000L
    private val maxAttempts = 3

    fun generateOtp(email: String): String {
        val otp = generateSixDigitOtp()
        val expiresAt = System.currentTimeMillis() + otpExpiryMillis

        otpStore[email] = OtpRecord(
            otp = otp,
            expiresAt = expiresAt,
            attemptsLeft = maxAttempts
        )

        return otp
    }

    fun validateOtp(email: String, inputOtp: String): OtpResult {
        val record = otpStore[email] ?: return OtpResult.NoOtp

        val now = System.currentTimeMillis()

        if (now > record.expiresAt) {
            return OtpResult.Expired
        }

        if (record.attemptsLeft == 0) {
            return OtpResult.TooManyAttempts
        }

        if (record.otp != inputOtp) {
            otpStore[email] = record.copy(
                attemptsLeft = record.attemptsLeft - 1
            )
            return OtpResult.Invalid
        }

        otpStore.remove(email)
        return OtpResult.Success
    }

    private fun generateSixDigitOtp(): String {
        val number = Random.nextInt(0, 1_000_000)
        return number.toString().padStart(6, '0')
    }
}

sealed class OtpResult {
    object Success : OtpResult()
    object Invalid : OtpResult()
    object Expired : OtpResult()
    object TooManyAttempts : OtpResult()
    object NoOtp : OtpResult()
}