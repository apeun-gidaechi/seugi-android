package com.seugi.starter

import android.content.Context
import android.content.Intent
import com.seugi.MainActivity
import com.seugi.common.utiles.SeugiActivityStarter

class SeugiActivityStarterImpl(
    private val context: Context,
) : SeugiActivityStarter {
    override fun reStartActivity() {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }
}
