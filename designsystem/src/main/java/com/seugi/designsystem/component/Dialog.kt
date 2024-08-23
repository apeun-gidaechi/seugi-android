package com.seugi.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.seugi.designsystem.animation.ButtonState
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.modifier.DropShadowType
import com.seugi.designsystem.component.modifier.dropShadow
import com.seugi.designsystem.theme.SeugiTheme

@Composable
fun SeugiDialog(title: String, content: String, onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Box(
            modifier = Modifier
                .dropShadow(DropShadowType.EvBlack2)
                .background(
                    color = SeugiTheme.colors.white,
                    shape = RoundedCornerShape(16.dp),
                ),
        ) {
            Column(
                modifier = Modifier
                    .padding(18.dp),
            ) {
                Column(
                    modifier = Modifier.padding(4.dp),
                ) {
                    Text(
                        text = title,
                        color = SeugiTheme.colors.black,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = content,
                        color = SeugiTheme.colors.gray700,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable(
                                onClick = onDismissRequest,
                                role = Role.Button,
                            ),
                    ) {
                        Text(
                            modifier = Modifier.padding(
                                horizontal = 12.dp,
                                vertical = (7.5).dp,
                            ),
                            text = "닫기",
                            color = SeugiTheme.colors.primary500,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SeugiDialog(title: String, content: String, leftText: String = "취소", rightText: String = "확인", onRightRequest: () -> Unit, onLeftRequest: () -> Unit, onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Box(
            modifier = Modifier
                .dropShadow(DropShadowType.EvBlack2)
                .background(
                    color = SeugiTheme.colors.white,
                    shape = RoundedCornerShape(16.dp),
                ),
        ) {
            Column(
                modifier = Modifier
                    .padding(18.dp),
            ) {
                Column(
                    modifier = Modifier.padding(4.dp),
                ) {
                    Text(
                        text = title,
                        color = SeugiTheme.colors.black,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = content,
                        color = SeugiTheme.colors.gray700,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    DialogButton(
                        modifier = Modifier.weight(1f),
                        text = leftText,
                        textColor = SeugiTheme.colors.gray600,
                        backgroundColor = SeugiTheme.colors.gray100,
                        onClick = onLeftRequest,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    DialogButton(
                        modifier = Modifier.weight(1f),
                        text = rightText,
                        textColor = SeugiTheme.colors.white,
                        backgroundColor = SeugiTheme.colors.primary500,
                        onClick = onRightRequest,
                    )
                }
            }
        }
    }
}

@Composable
private fun DialogButton(modifier: Modifier, text: String, textColor: Color, backgroundColor: Color, onClick: () -> Unit) {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    Box(
        modifier = modifier
            .height(54.dp)
            .bounceClick(
                onClick = onClick,
                onChangeButtonState = {
                    buttonState = it
                },
            ),
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    color =
                    if (buttonState == ButtonState.Idle) {
                        backgroundColor
                    } else {
                        backgroundColor.copy(alpha = 0.7f)
                    },
                    shape = RoundedCornerShape(12.dp),
                ),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                color = textColor,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}
