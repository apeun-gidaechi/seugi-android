package com.seugi.start

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.LocalContext as LocalContext1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StartScreen(
    navigateToEmailSignIn: () -> Unit,
    viewModel: StartViewModel = hiltViewModel()
) {
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
    val clientId = stringResource(com.seugi.designsystem.R.string.server_id)
    val googleSignInOption = GoogleSignInOptions
        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestServerAuthCode(clientId)
        .requestEmail()
        .requestProfile()
        .build()
    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, googleSignInOption)

    val googleAuthLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val code = account.serverAuthCode.toString()
            viewModel.googleLogin(code = code)
            Log.d("TAG", "code: ${account.serverAuthCode}")
            Toast.makeText(context, "로그인 성공", Toast.LENGTH_SHORT).show()
        } catch (e: ApiException) {
            Log.e("TAG", "Google Sign-In 실패: ${e.statusCode} - ${e.message}")
            Toast.makeText(context, "로그인 실패: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    SeugiTheme {
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

            AnimatedVisibility(
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
                        .fillMaxSize()
                        .padding(bottom = 20.dp),
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
                            Spacer(modifier = Modifier.height(8.dp))
                            SeugiOAuthButton(
                                image = R.drawable.ic_apple,
                                text = "Apple로 계속하기",
                                onClick = {
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
