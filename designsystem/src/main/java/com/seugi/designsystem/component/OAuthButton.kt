package com.seugi.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.ButtonState
import com.seugi.designsystem.theme.SeugiTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SeugiOAuthButton(@DrawableRes image: Int, text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(
        targetValue = if (buttonState == ButtonState.Idle) 1f else 0.96f,
        label = "",
    )

    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = SeugiTheme.colors.white,
            contentColor = SeugiTheme.colors.black,
            disabledContainerColor = SeugiTheme.colors.white.copy(alpha = 0.5f),
            disabledContentColor = SeugiTheme.colors.white.copy(alpha = 0.5f),
        ),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke((1.5).dp, SeugiTheme.colors.gray300),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp),
            )
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Text(
                text = text,
                style = SeugiTheme.typography.subtitle2,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSeugiOAuthButton() {
    SeugiTheme {
        var testState by remember { mutableStateOf(true) }
        val coroutineScope = rememberCoroutineScope()
        val onClick: () -> Unit = {
            coroutineScope.launch {
                testState = false
                delay(2000)
                testState = true
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SeugiOAuthButton(image = R.drawable.test_icon, text = "Google로 계속하기", onClick = onClick)
        }
    }
}
