package com.seugi.network.core

object SeugiUrl {
    private const val BASE_URL = BuildConfig.BASE_URL
    private const val AI_URL = BuildConfig.AI_URL

    const val WORKSPACE = "$BASE_URL/workspace"
    const val FILE = "$BASE_URL/file"
    const val AI = "$AI_URL/ai"
    const val PROFILE = "$BASE_URL/profile"
    const val OAUTH = "$BASE_URL/oauth"
    const val SCHEDULE = "$BASE_URL/schedule"

    object Message {
        private const val WS_URL = BuildConfig.WS_URL
        const val HANDSHAKE = "${WS_URL}/stomp/chat"
        const val SUBSCRIPTION = "/exchange/chat.exchange/room."
        const val VIRTUALHOST = "/"
        const val SEND = "/pub/chat.message"
        const val GET_MESSAGE = "${BASE_URL}/message/search"
    }

    object PersonalChat {
        const val ROOT = "${BASE_URL}/chat/personal"
        const val LOAD_ALL = "$ROOT/search"
        const val CREATE = "$ROOT/create"
        const val SEARCH_ROOM = "$ROOT/search/room"
    }

    object GroupChat {
        const val ROOT = "${BASE_URL}/chat/group"
        const val LOAD_ALL = "$ROOT/search"
        const val CREATE = "$ROOT/create"
        const val SEARCH_ROOM = "$ROOT/search/room"
        const val LEFT = "$ROOT/left"
    }

    object Profile {
        const val ME = "$PROFILE/me"
    }

    object Auth {
        const val EMAIL_SIGN_IN = "$BASE_URL/member/login"
        const val GET_CODE = "$BASE_URL/email/send"
        const val EMAIL_SIGN_UP = "$BASE_URL/member/register"
    }

    object Workspace {
        const val MEMBERS = "$WORKSPACE/members"
        const val CHECK_WORKSPACE = "$WORKSPACE/search"
        const val APPLICATION = "$WORKSPACE/join"
        const val PERMISSION = "$WORKSPACE/permission"
        const val GET_MY_WORKSPACES = WORKSPACE
        const val GET_MY_WAIT_WORKSPACES = "$WORKSPACE/my/wait-list"
    }

    object Notification {
        const val ROOT = "${BASE_URL}/notification"
        const val EMOJI = "$ROOT/emoji"
    }

    object Member {
        const val ROOT = "${BASE_URL}/member"
        const val REFRESH = "$ROOT/refresh"
        const val EDIT = "$ROOT/edit"
    }

    object File {
        const val FILE_UPLOAD = "$FILE/upload"
    }

    object Meal {
        const val ROOT = "${BASE_URL}/meal"
    }

    object Timetable {
        const val ROOT = "${BASE_URL}/timetable"
        const val DAY = "$ROOT/day"
    }

    object Oauth {
        const val GOOGLE_AUTHENTICATE = "$OAUTH/google/authenticate"
    }

    object Schedule {
        const val MONTH = "$SCHEDULE/month"
    }
}
