package com.seugi.profile.model

sealed interface ProfileSideEffect {
    data class FailedChange(val throwable: Throwable) : ProfileSideEffect
}
