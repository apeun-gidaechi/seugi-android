package com.apeun.gidaechi.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.component.modifier.DropShadowType
import com.apeun.gidaechi.designsystem.component.modifier.dropShadow
import com.apeun.gidaechi.designsystem.theme.Gray500
import com.apeun.gidaechi.designsystem.theme.Primary100
import com.apeun.gidaechi.designsystem.theme.Primary500
import com.apeun.gidaechi.designsystem.theme.Red100
import com.apeun.gidaechi.designsystem.theme.Red200
import com.apeun.gidaechi.designsystem.theme.Red300
import com.apeun.gidaechi.designsystem.theme.Red500
import com.apeun.gidaechi.designsystem.theme.SeugiTheme
import com.apeun.gidaechi.designsystem.theme.Transparent
import com.apeun.gidaechi.designsystem.theme.White

sealed class ButtonType(
    val textColor: Color,
    val backgroundColor: Color,
    val disableTextColor: Color,
    val disableBackgroundColor: Color
) {
    data object Primary: ButtonType(White, Primary500, White, Primary100)
    data object Black: ButtonType(White, com.apeun.gidaechi.designsystem.theme.Black, White, Gray500)
    data object Red: ButtonType(Red500, Red200, Red300, Red100)
    data object Transparent: ButtonType(com.apeun.gidaechi.designsystem.theme.Black, com.apeun.gidaechi.designsystem.theme.Transparent, Gray500, com.apeun.gidaechi.designsystem.theme.Transparent)
    data object Shadow: ButtonType(com.apeun.gidaechi.designsystem.theme.Black, White, Gray500, White)
}


@Composable
fun SeugiFullWidthButton(
    onClick: () -> Unit,
    type: ButtonType,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(12.dp),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val buttonModifier =
        if (type is ButtonType.Shadow)
            Modifier
                .dropShadow(DropShadowType.Ev1)
                .background(White)
        else Modifier
    Box(modifier = buttonModifier) {
        Button(
            onClick = onClick,
            modifier = buttonModifier
                .then(modifier)
                .fillMaxWidth()
                .height(54.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = type.backgroundColor,
                contentColor = type.textColor,
                disabledContainerColor = type.disableBackgroundColor,
                disabledContentColor = type.disableTextColor
            ),
            enabled = enabled,
            shape = shape,
            contentPadding = contentPadding,
            interactionSource = interactionSource
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun SeugiButtonPreview() {
    SeugiTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .background(White)
        ) {
            SeugiFullWidthButton(
                onClick = {  },
                type = ButtonType.Primary,
                text = "시작하기"
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiFullWidthButton(
                onClick = {  },
                type = ButtonType.Black,
                text = "시작하기"
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiFullWidthButton(
                onClick = {  },
                type = ButtonType.Red,
                text = "시작하기"
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiFullWidthButton(
                onClick = {  },
                type = ButtonType.Transparent,
                text = "시작하기"
            )
            Spacer(modifier = Modifier.height(10.dp))
            SeugiFullWidthButton(
                onClick = {  },
                type = ButtonType.Shadow,
                text = "시작하기"
            )
        }
    }
}