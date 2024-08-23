package com.seugi.workspace.feature

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.ButtonType
import com.seugi.designsystem.component.SeugiFullWidthButton
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.theme.SeugiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SelectingJobScreen(navigateToSelectingRole: (role: String) -> Unit, popBackStack: () -> Unit) {
    var studentOnOff by remember {
        mutableStateOf(true)
    }

    val studentPainter = painterResource(id = R.drawable.img_student)
    val teacherPainter = painterResource(id = R.drawable.img_teacher)

    SeugiTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                SeugiTopBar(
                    title = { Text(text = "학교 가입", style = SeugiTheme.typography.subtitle1) },
                    onNavigationIconClick = popBackStack,
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 16.dp),
            ) {
                Spacer(modifier = Modifier.weight(0.4f))
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Row(
                        modifier = Modifier.padding(start = 4.dp, bottom = 8.dp),
                    ) {
                        Text(
                            text = "학생이신가요?\n" + "아니면 선생님이신가요?",
                            style = SeugiTheme.typography.subtitle1,
                            modifier = Modifier.bounceClick({}),
                        )
                    }
                    Row {
                        Box(
                            modifier = Modifier
                                .bounceClick({ studentOnOff = true })
                                .fillMaxHeight()
                                .weight(1f)
                                .background(shape = RoundedCornerShape(12.dp), color = SeugiTheme.colors.gray100)
                                .border(
                                    border = BorderStroke(
                                        1.dp,
                                        color = if (studentOnOff) SeugiTheme.colors.primary500 else SeugiTheme.colors.gray100,
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                ),

                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Spacer(modifier = Modifier.weight(1f))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                ) {
                                    Text(
                                        text = "학생",
                                        style = SeugiTheme.typography.subtitle2,
                                        color = if (studentOnOff) SeugiTheme.colors.black else SeugiTheme.colors.gray500,
                                    )
                                    if (studentOnOff) {
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Image(
                                            painter = painterResource(id = R.drawable.img_check),
                                            contentDescription = "",
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Image(
                                    painter = studentPainter,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(152.dp)
                                        .offset(y = (4).dp),
                                    contentScale = ContentScale.Fit,
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .bounceClick(onClick = { studentOnOff = false })
                                .fillMaxHeight()
                                .weight(1f)
                                .background(shape = RoundedCornerShape(12.dp), color = SeugiTheme.colors.gray100)
                                .border(
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = if (!studentOnOff) SeugiTheme.colors.primary500 else SeugiTheme.colors.gray100,
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                ),
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Spacer(modifier = Modifier.weight(1f))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                ) {
                                    Text(
                                        text = "선생님",
                                        style = SeugiTheme.typography.subtitle2,
                                        color = if (!studentOnOff) SeugiTheme.colors.black else SeugiTheme.colors.gray500,
                                    )
                                    if (!studentOnOff) {
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Image(
                                            painter = painterResource(id = R.drawable.img_check),
                                            contentDescription = "",
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Image(
                                    painter = teacherPainter,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(152.dp)
                                        .offset(y = (4).dp),
                                    contentScale = ContentScale.Fit,
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    SeugiFullWidthButton(
                        onClick = {
                            navigateToSelectingRole(
                                if (studentOnOff) "STUDENT" else "TEACHER",
                            )
                        },
                        type = ButtonType.Primary,
                        text = "계속하기",
                        modifier = Modifier.padding(vertical = 16.dp),
                    )
                }
            }
        }
    }
}
