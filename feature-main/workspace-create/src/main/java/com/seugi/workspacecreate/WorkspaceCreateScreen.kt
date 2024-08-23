package com.seugi.workspacecreate

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seugi.designsystem.R
import com.seugi.designsystem.component.ButtonType
import com.seugi.designsystem.component.SeugiFullWidthButton
import com.seugi.designsystem.component.SeugiIconButton
import com.seugi.designsystem.component.SeugiRoundedCircleImage
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.Size
import com.seugi.designsystem.component.textfield.SeugiTextField
import com.seugi.designsystem.theme.SeugiTheme
import com.seugi.ui.CollectAsSideEffect
import com.seugi.workspacecreate.model.WorkspaceCreateSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkspaceCreateScreen(popBackStack: () -> Unit, viewModel: WorkspaceCreateViewModel = hiltViewModel()) {
    var schoolNameText by remember { mutableStateOf("") }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    var schoolNameError by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    viewModel.sideEffect.CollectAsSideEffect {
        when (it) {
            is WorkspaceCreateSideEffect.Error -> {
                Toast.makeText(context, it.throwable.message, Toast.LENGTH_SHORT).show()
            }
            is WorkspaceCreateSideEffect.SuccessCreate -> {
                Toast.makeText(context, "워크페이스가 성공적으로 등록되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    SeugiTheme {
        Scaffold(
            topBar = {
                SeugiTopBar(
                    title = {
                        Text(text = "새 학교 등록", style = SeugiTheme.typography.subtitle1)
                    },
                    onNavigationIconClick = {
                        popBackStack()
                    },
                )
            },

        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Box(
                        contentAlignment = Alignment.BottomEnd,
                    ) {
                        if (selectedImageUri != null) {
                            SeugiRoundedCircleImage(
                                image = selectedImageUri.toString(),
                                size = Size.Small,
                                onClick = {},
                            )
                        } else {
                            SeugiRoundedCircleImage(
                                size = Size.Small,
                                onClick = {},
                            )
                        }
                        SeugiIconButton(
                            resId = R.drawable.ic_add_fill,
                            onClick = { galleryLauncher.launch("image/*") },
                            colors = IconButtonDefaults.iconButtonColors(contentColor = SeugiTheme.colors.gray600),
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                ) {
                    Column {
                        Row(
                            modifier = Modifier.padding(start = 4.dp),
                        ) {
                            Text(text = "학교 이름", style = SeugiTheme.typography.subtitle2)
                            Text(
                                text = " *",
                                style = SeugiTheme.typography.subtitle2,
                                color = SeugiTheme.colors.red500,
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        SeugiTextField(
                            value = schoolNameText,
                            onValueChange = { text ->
                                schoolNameText = text
                            },
                            onClickDelete = { schoolNameText = "" },
                            placeholder = "학교 이름을 입력해 주세요",
                        )
                        if (schoolNameError) {
                            Text(
                                text = "이메일을 입력해 주세요",
                                style = SeugiTheme.typography.body1,
                                color = SeugiTheme.colors.red500,
                                modifier = Modifier.padding(top = 4.dp),
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                SeugiFullWidthButton(
                    onClick = {
                        schoolNameError = schoolNameText.isEmpty()
                        if (!schoolNameError) {
                            viewModel.fileUpload(
                                workspaceName = schoolNameText,
                                context = context,
                                workspaceUri = selectedImageUri,
                            )
                        }
                    },
                    type = ButtonType.Primary,
                    text = "등록하기",
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(vertical = 16.dp),
                )
            }
        }
    }
}
