package com.ramgdev.shoppa

import android.app.Application
import co.paystack.android.PaystackSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ShoppaApp: Application() {
    override fun onCreate() {
        super.onCreate()

        PaystackSdk.initialize(applicationContext)
        PaystackSdk.setPublicKey(BuildConfig.PSTK_PUBLIC_KEY)
        initTimber()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}