package com.seugi.start.model


data class StartUiState (
    val loginState: LoginState<Nothing> = LoginState.Init
)

sealed interface LoginState<out T>{
    data object Success : LoginState<Nothing>
    data object Loading : LoginState<Nothing>
    data object Error : LoginState<Nothing>
    data object Init : LoginState<Nothing>
}