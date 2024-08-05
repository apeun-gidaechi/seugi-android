package com.seugi.network.core

object SeugiUrl {
    private const val BASE_URL = BuildConfig.BASE_URL

    const val WORKSPACE = "$BASE_URL/workspace"

    object Message {
        private const val WS_URL = BuildConfig.WS_URL
        const val HANDSHAKE = "${WS_URL}/stomp/chat"
        const val SUBSCRIPTION = "/exchange/chat.exchange/room."
        const val VIRTUALHOST = "/"
        const val GET_MESSAGE = "${BASE_URL}/message/search"
    }

    object Chat {
        const val ROOT = "${BASE_URL}/chat"
        const val LOAD_MEMBER = ""
        const val LEFT = "${BASE_URL}/chat/group/left"
    }

    object PersonalChat {
        const val ROOT = "${BASE_URL}/chat/personal"
        const val LOAD_ALL = "$ROOT/search"
        const val CREATE = "$ROOT/create"
    }

    object GroupChat {
        const val ROOT = "${BASE_URL}/chat/group"
        const val LOAD_ALL = "$ROOT/search"
        const val CREATE = "$ROOT/create"
    }

    object Profile {
        const val ME = "${BASE_URL}/profile/me"
    }

    object Auth {
        const val EMAIL_SIGN_IN = "$BASE_URL/member/login"
        const val GET_CODE = "$BASE_URL/email/send?email="
        const val EMAIL_SIGN_UP = "$BASE_URL/member/register"
    }

    object Workspace {
        const val ROOT = "$WORKSPACE/"
        const val MEMBERS = "$ROOT/members"
        const val CHECK_WORKSPACE = "$WORKSPACE/search/"
        const val APPLICATION = "$WORKSPACE/join"
    }

    object Notice {
        const val ROOT = "${BASE_URL}/notice"
    }

    object Member {
        const val ROOT = "${BASE_URL}/member"
        const val REFRESH = "$ROOT/refresh"
    }
}
