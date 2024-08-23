package com.seugi.designsystem.component.textfield

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.R
import com.seugi.designsystem.component.modifier.DropShadowType
import com.seugi.designsystem.component.modifier.dropShadow
import com.seugi.designsystem.theme.SeugiTheme

/**
 * Seugi Chat TextField
 *
 * @param modifier the Modifier to be applied to this text field
 * @param value the means the value to be seen.
 * @param placeholder the indicates the value to be displayed before the value is entered.
 * @param onValueChange the event is forwarded when the value changes.
 * @param onAddClick the event is click add icon.
 * @param onSendClick the event is click send icon.
 */
@Composable
fun SeugiChatTextField(modifier: Modifier = Modifier, value: String, placeholder: String = "", onValueChange: (String) -> Unit, onAddClick: () -> Unit = {}, onSendClick: () -> Unit = {}) {
    val valueIsEmpty = value.isEmpty()

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .dropShadow(DropShadowType.EvBlack1),
        shape = RoundedCornerShape(12.dp),
        color = SeugiTheme.colors.white,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 200.dp)
                .padding(
                    horizontal = 16.dp,
                    vertical = 14.dp,
                ),
            verticalAlignment = Alignment.Bottom,
        ) {
            Image(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .clickable(
                        role = Role.Button,
                        onClick = onAddClick,
                    ),
                painter = painterResource(id = R.drawable.ic_add_fill),
                contentDescription = "add button",
                colorFilter = ColorFilter.tint(SeugiTheme.colors.gray400),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f),
            ) {
                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = value,
                    textStyle = MaterialTheme.typography.titleMedium,
                    onValueChange = onValueChange,
                    decorationBox = { innerTextField ->
                        Box {
                            if (valueIsEmpty) {
                                Text(
                                    text = placeholder,
                                    color = SeugiTheme.colors.gray500,
                                    style = MaterialTheme.typography.titleMedium,
                                )
                            }
                            innerTextField()
                        }
                    },
                )
                Spacer(modifier = Modifier.height(5.5.dp))
            }

            Image(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .clickable(
                        role = Role.Button,
                        onClick = onSendClick,
                        enabled = !valueIsEmpty,
                    ),
                painter = painterResource(id = R.drawable.ic_send_fill),
                contentDescription = "send button",
                colorFilter = ColorFilter.tint(if (valueIsEmpty) SeugiTheme.colors.gray400 else SeugiTheme.colors.primary500),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSeugiChatTextField() {
    var value by remember { mutableStateOf("") }
    SeugiTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            SeugiChatTextField(
                value = value,
                placeholder = "메세지 보내기",
                onValueChange = {
                    value = it
                },
                onAddClick = {
                },
                onSendClick = {
                },
            )
        }
    }
}
