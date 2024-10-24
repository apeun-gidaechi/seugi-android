package com.seugi.start

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext as LocalContext1
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.seugi.designsystem.component.ButtonType
import com.seugi.designsystem.component.GradientPrimary
import com.seugi.designsystem.component.SeugiFullWidthButton
import com.seugi.designsystem.component.SeugiOAuthButton
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.start.model.LoginState
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StartScreen(navigateToEmailSignIn: () -> Unit, navigateToMain: () -> Unit, viewModel: StartViewModel = hiltViewModel()) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var visibleCloud1 by remember { mutableStateOf(false) }
    var visibleText by remember { mutableStateOf(false) }
    var visibleCloud2 by remember { mutableStateOf(false) }
    var visibleButton by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        visibleCloud1 = true
        delay(500)
        visibleText = true
        visibleCloud2 = true
        delay(500)
        visibleButton = true
    }

    val context = LocalContext1.current
    val clientId = stringResource(R.string.server_id)
    val googleSignInOption = GoogleSignInOptions
        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestServerAuthCode(clientId)
        .requestEmail()
        .requestScopes(
            Scope(Scopes.PROFILE),
            Scope(Scopes.EMAIL),
            Scope("https://www.googleapis.com/auth/classroom.courses.readonly"),
            Scope("https://www.googleapis.com/auth/classroom.coursework.me.readonly"),
            Scope("https://www.googleapis.com/auth/classroom.coursework.students.readonly"),

        )
        .build()
    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, googleSignInOption)

    val googleAuthLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        Log.d("", "StartScreen: ${result.resultCode == Activity.RESULT_CANCELED}")
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val code = account.serverAuthCode.toString()
            viewModel.getFcmToken(code = code)
        } catch (e: ApiException) {
            Log.d("TAG", "StartScreen: $e")
            Toast.makeText(context, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    when (state.loginState) {
        LoginState.Error -> {
            Toast.makeText(context, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
        }
        LoginState.Loading -> {
            Dialog(
                onDismissRequest = {
                    state.loginState != LoginState.Loading
                },
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                ),
            ) {
                CircularProgressIndicator(
                    color = SeugiTheme.colors.white,
                )
            }
        }
        LoginState.Success -> {
            navigateToMain()
        }

        LoginState.Init -> {
        }
    }

    SeugiTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = GradientPrimary,
                    ),
            ) {
                Spacer(modifier = Modifier.height(70.dp))

                AnimatedVisibility(
                    visible = visibleCloud1,
                    enter = slideInVertically(
                        initialOffsetY = { 120 },
                        animationSpec = tween(durationMillis = 1000),
                    ) + fadeIn(
                        initialAlpha = 0f,
                        animationSpec = tween(durationMillis = 1000),
                    ),
                ) {
                    Box(modifier = Modifier.padding(bottom = 20.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_cloud1),
                                contentDescription = "",
                            )
                        }
                    }
                }

                AnimatedVisibility(
                    visible = visibleText,
                    enter = slideInVertically(
                        initialOffsetY = { 120 },
                        animationSpec = tween(durationMillis = 1000),
                    ) + fadeIn(
                        initialAlpha = 0f,
                        animationSpec = tween(durationMillis = 1000),
                    ),
                ) {
                    Box(modifier = Modifier.padding(bottom = 20.dp)) {
                        Column(
                            modifier = Modifier.padding(start = 24.dp),
                        ) {
                            Text(
                                text = "스기",
                                style = SeugiTheme.typography.display1,
                                color = SeugiTheme.colors.white,
                            )
                            Text(
                                text = "학생, 선생님 모두 함께하는\n스마트 스쿨 플랫폼",
                                style = SeugiTheme.typography.subtitle2,
                                color = SeugiTheme.colors.white,
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                AnimatedVisibility(
                    visible = visibleCloud2,
                    enter = slideInVertically(
                        initialOffsetY = { 120 },
                        animationSpec = tween(durationMillis = 1000),
                    ) + fadeIn(
                        initialAlpha = 0f,
                        animationSpec = tween(durationMillis = 1000),
                    ),
                ) {
                    Box(modifier = Modifier.padding(bottom = 20.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_cloud2),
                                contentDescription = "",
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp),
                visible = visibleButton,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = 1000),
                ) + fadeIn(
                    initialAlpha = 0f,
                    animationSpec = tween(durationMillis = 1000),
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    SeugiFullWidthButton(
                        onClick = { showBottomSheet = true },
                        type = ButtonType.Transparent,
                        text = "시작하기",
                        modifier = Modifier
                            .padding(horizontal = 20.dp),
                    )
                }
            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState,
                    containerColor = SeugiTheme.colors.white,
                    dragHandle = null,
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp),
                    ) {
                        Column {
                            SeugiFullWidthButton(
                                onClick = {
                                    navigateToEmailSignIn()
                                    showBottomSheet = false
                                },
                                type = ButtonType.Black,
                                text = "이메일로 계속하기",
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            SeugiOAuthButton(
                                image = R.drawable.ic_google,
                                text = "Google로 계속하기",
                                onClick = {
                                    googleSignInClient.signOut()
                                    val signInIntent = googleSignInClient.signInIntent
                                    googleAuthLauncher.launch(signInIntent)
                                    showBottomSheet = false
                                },
                            )
                        }
                        Spacer(modifier = Modifier.height(50.dp))
                    }
                }
            }
        }
    }
}
