package com.seugi.designsystem.preview.feature

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.seugi.designsystem.preview.NavRoot
import com.seugi.designsystem.theme.SeugiTheme

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        HomeButton(
            text = "CHATITEM",
            onClick = {
                navController.navigate(NavRoot.CHATITEM)
            },
        )
        HomeButton(
            text = "CHATLIST",
            onClick = {
                navController.navigate(NavRoot.CHATLIST)
            },
        )
        HomeButton(
            text = "AVATAR",
            onClick = {
                navController.navigate(NavRoot.AVATAR)
            },
        )
        HomeButton(
            text = "BADGE",
            onClick = {
                navController.navigate(NavRoot.BADGE)
            },
        )
        HomeButton(
            text = "BOTTOMNAV",
            onClick = {
                navController.navigate(NavRoot.BOTTOMNAV)
            },
        )
        HomeButton(
            text = "BUTTON",
            onClick = {
                navController.navigate(NavRoot.BUTTON)
            },
        )

        HomeButton(
            text = "CATEGORY",
            onClick = {
                navController.navigate(NavRoot.CATEGORY)
            },
        )
        HomeButton(
            text = "CHECKBOX",
            onClick = {
                navController.navigate(NavRoot.CHECKBOX)
            },
        )
        HomeButton(
            text = "DIVIDER",
            onClick = {
                navController.navigate(NavRoot.DIVIDER)
            },
        )
        HomeButton(
            text = "LISTIEM",
            onClick = {
                navController.navigate(NavRoot.LISTIEM)
            },
        )
        HomeButton(
            text = "MEMBERLIST",
            onClick = {
                navController.navigate(NavRoot.MEMBERLIST)
            },
        )
        HomeButton(
            text = "OAUTHBUTTON",
            onClick = {
                navController.navigate(NavRoot.OAUTHBUTTON)
            },
        )
        HomeButton(
            text = "TOGGLE",
            onClick = {
                navController.navigate(NavRoot.TOGGLE)
            },
        )
        HomeButton(
            text = "SHADOW",
            onClick = {
                navController.navigate(NavRoot.SHADOW)
            },
        )
        HomeButton(
            text = "DROPDOWN",
            onClick = {
                navController.navigate(NavRoot.DROPDOWN)
            },
        )
        HomeButton(
            text = "TOPBAR",
            onClick = {
                navController.navigate(NavRoot.TOPBAR)
            },
        )
        HomeButton(
            text = "TOOLTIP",
            onClick = {
                navController.navigate(NavRoot.TOOLTIP)
            },
        )
        HomeButton(
            text = "DIALOG",
            onClick = {
                navController.navigate(NavRoot.DIALOG)
            },
        )
        HomeButton(
            text = "ERROR",
            onClick = {
                navController.navigate(NavRoot.ERROR)
            },
        )
        HomeButton(
            text = "ROOMIMAGE",
            onClick = {
                navController.navigate(NavRoot.ROOMIMAGE)
            },
        )
    }
}

@Composable
private fun HomeButton(text: String, onClick: () -> Unit) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable(
                    onClick = onClick,
                )
                .border(
                    width = 1.dp,
                    color = SeugiTheme.colors.black,
                    shape = RoundedCornerShape(8.dp),
                ),
        ) {
            Text(
                text = text,
                style = SeugiTheme.typography.subtitle2,
                color = SeugiTheme.colors.black,
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}
