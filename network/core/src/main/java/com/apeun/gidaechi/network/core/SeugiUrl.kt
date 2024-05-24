package com.apeun.gidaechi.network.core

object SeugiUrl {
    private const val BASE_URL = BuildConfig.BASE_URL

    object Chat {
        private const val WS_URL = BuildConfig.WS_URL
        const val HANDSHAKE = "${WS_URL}/stomp/chat"
        const val SUBSCRIPTION = "/exchange/chat.exchange/room."
        const val VIRTUALHOST = "/"
    }

    object Auth {
        const val EMAIL_SIGN_IN = "$BASE_URL/member/login"
    }
}
