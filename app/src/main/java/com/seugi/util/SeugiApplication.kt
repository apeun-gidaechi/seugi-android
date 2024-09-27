package com.seugi.util

import android.app.Application
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SeugiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // emulate URI exposure fix
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }
}
