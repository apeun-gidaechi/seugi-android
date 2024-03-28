package com.apeun.gidaechi.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.component.modifier.DropShadowType
import com.apeun.gidaechi.designsystem.component.modifier.dropShadow
import com.apeun.gidaechi.designsystem.theme.Gray200
import com.apeun.gidaechi.designsystem.theme.Primary500
import com.apeun.gidaechi.designsystem.theme.SeugiTheme
import com.apeun.gidaechi.designsystem.theme.Transparent
import com.apeun.gidaechi.designsystem.theme.White

@Composable
@Preview
fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Toggle(checked = true)
    }
}


@Composable
fun Toggle(
    checked: Boolean,
    modifier: Modifier = Modifier
) {

    var isChecked by remember { mutableStateOf(checked) }



    SeugiTheme {
        Switch(
            checked = isChecked,
            onCheckedChange = { isChecked = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = White,
                checkedTrackColor = Primary500,
                uncheckedThumbColor = White,
                uncheckedTrackColor = Gray200,
                uncheckedBorderColor = Transparent

            ),
            modifier = Modifier
                .height(31.dp)
                .width(51.dp),
            thumbContent = {
                Box(modifier = Modifier
                    .size(27.dp)
                    .background(
                        color = White,
                        shape = CircleShape
                    )
                    .dropShadow(DropShadowType.Ev2)
                )
            },
        )
    }
}
