package com.apeun.gidaechi.network.core

object SeugiUrl {
    private val BASE_URL = BuildConfig.BASE_URL

    object Chat {
        private val WS_URL = BuildConfig.WS_URL
        val HANDSHAKE = "${WS_URL}/stomp/chat"
        val SUBSCRIPTION = "/exchange/chat.exchange/room."
        val VIRTUALHOST = "/"
    }

    object Auth {
        val EMAIL_SIGN_IN = "$BASE_URL/member/login"
    }
}
