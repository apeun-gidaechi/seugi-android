package com.seugi.network.core

object SeugiUrl {
    private const val BASE_URL = BuildConfig.BASE_URL
    private const val AI_URL = "${BASE_URL}/ai"

    const val WORKSPACE = "$BASE_URL/workspace"
    const val FILE = "$BASE_URL/file"
    const val AI = "$BASE_URL/ai"
    const val PROFILE = "$BASE_URL/profile"
    const val OAUTH = "$BASE_URL/oauth"
    const val SCHEDULE = "$BASE_URL/schedule"
    const val TASK = "$BASE_URL/task"
    const val MESSAGE = "$BASE_URL/message"

    object Message {
        private const val WS_URL = BuildConfig.WS_URL
        const val HANDSHAKE = "${WS_URL}/stomp/chat"
        const val SUBSCRIPTION = "/exchange/chat.exchange/room."
        const val VIRTUALHOST = "/"
        const val SEND = "/pub/chat.message"
        const val GET_MESSAGE = "${BASE_URL}/message/search"
        const val EMOJI = "${MESSAGE}/emoji"
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
        const val MEMBER_ADD = "$ROOT/member/add"
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
        const val GET_WAIT_MEMBERS = "$WORKSPACE/wait-list"
        const val GET_WORKSPACE_CODE = "$WORKSPACE/code"
        const val ADD_MEMBER = "$WORKSPACE/add"
        const val CANCEL_MEMBER = "$WORKSPACE/cancel"
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
        const val ALL = "$ROOT/all"
    }

    object Timetable {
        const val ROOT = "${BASE_URL}/timetable"
        const val DAY = "$ROOT/day"
        const val WEEKEND = "$ROOT/weekend"
    }

    object Oauth {
        const val GOOGLE_AUTHENTICATE = "$OAUTH/google/authenticate"
    }

    object Schedule {
        const val MONTH = "$SCHEDULE/month"
    }

    object Task {
        const val CLASSROOM = "$TASK/classroom"
    }
}
