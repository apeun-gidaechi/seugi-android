package com.seugi.start.model

import androidx.compose.foundation.interaction.DragInteraction
import com.seugi.login.model.EmailSignInSideEffect

sealed class StartSideEffect {
    data class FailedLogin(val throwable: Throwable) : StartSideEffect()
    data object SuccessLogin : StartSideEffect()
}