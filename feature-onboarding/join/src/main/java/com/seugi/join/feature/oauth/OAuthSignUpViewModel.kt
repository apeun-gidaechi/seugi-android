package com.seugi.join.feature.oauth

import androidx.activity.result.ActivityResult
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class OAuthSignUpViewModel @Inject constructor(

): ViewModel() {

    private val _isFailState = mutableStateOf(false)
    val isFailState: State<Boolean> = _isFailState

    fun login(
        activityResult: ActivityResult,
        onSuccess: () -> Unit
    ) {
        try {
            val account = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
                .getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) onSuccess()
                }
        } catch (e: Exception) {
            _isFailState.value = true
        }
    }

}