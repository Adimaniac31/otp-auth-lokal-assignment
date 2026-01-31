package com.example.otpauthapp

import android.app.Application
import timber.log.Timber

class OtpAuthApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}