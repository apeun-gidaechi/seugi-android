package com.seugi.util

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.seugi.data.firebasetoken.FirebaseTokenRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

@AndroidEntryPoint
class FirebaseMessageService : FirebaseMessagingService() {

    @Inject
    lateinit var firebaseRepository: FirebaseTokenRepository

    private val serviceScope = CoroutineScope(EmptyCoroutineContext)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TAG", "onNewToken: $token")
        serviceScope.launch(Dispatchers.IO) {
            firebaseRepository.insertToken(
                firebaseToken = token,
            )
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("TAG", "Notification Title :: ${message.notification?.title}")
        Log.d("TAG", "Notification Body :: ${message.notification?.body}")
        Log.d("TAG", "Notification Data :: ${message.data}")
    }


    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
