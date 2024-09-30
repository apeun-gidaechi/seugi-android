package com.seugi.util

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import javax.inject.Inject

class FirebaseMessageService: FirebaseMessagingService() {


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TAG", "firebase Token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("TAG", "Notification Title :: ${message.notification?.title}")
        Log.d("TAG", "Notification Body :: ${message.notification?.body}")
        Log.d("TAG", "Notification Data :: ${message.data}")
    }
}