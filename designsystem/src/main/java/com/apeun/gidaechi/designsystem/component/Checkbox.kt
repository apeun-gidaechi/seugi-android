package com.apeun.gidaechi.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.R
import com.apeun.gidaechi.designsystem.theme.Gray500
import com.apeun.gidaechi.designsystem.theme.Primary500
import com.apeun.gidaechi.designsystem.theme.SeugiTheme

sealed class CheckBoxType(val size: Dp) {
    data object Small: CheckBoxType(16.dp)
    data object Medium: CheckBoxType(20.dp)
    data object Large: CheckBoxType(24.dp)
}

/**
 * Seugi Check Box
 *
 * @param modifier the Modifier to be applied to this Check Box.
 * @param type the means checkbox Size.
 * @param checked the indicates status of the check box.
 * @param enabled the indicates whether it is clickable or not.
 * @param onCheckedChange An event occurs when the checkbox is pressed.
 */
@Composable
fun SeugiCheckbox(
    modifier: Modifier = Modifier,
    type: CheckBoxType,
    checked: Boolean,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit,
) {
    Image(
        modifier = modifier
            .size(type.size)
            .clip(CircleShape)
            .clickable(
                enabled = enabled,
                onClick = {
                    onCheckedChange(!checked)
                },
                role = Role.Checkbox,

            ),
        painter = painterResource(id = if (checked) R.drawable.ic_check_fill else R.drawable.ic_check_line),
        contentDescription = "checkBox",
        colorFilter = ColorFilter.tint(if (checked && enabled) Primary500 else Gray500)
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSeugiCheckBox() {
    var checked by remember { mutableStateOf(true) }

    SeugiTheme {
        Column(modifier = Modifier.fillMaxSize()) {

            SeugiCheckbox(
                type = CheckBoxType.Small,
                checked = checked
            ) {
                checked = it
            }
            Spacer(modifier = Modifier.height(10.dp))
            SeugiCheckbox(
                type = CheckBoxType.Medium,
                checked = checked
            ) {
                checked = it
            }
            Spacer(modifier = Modifier.height(10.dp))
            SeugiCheckbox(
                type = CheckBoxType.Large,
                checked = checked
            ) {
                checked = it
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}