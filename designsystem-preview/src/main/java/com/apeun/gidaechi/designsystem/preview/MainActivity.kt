package com.seugi.designsystem.preview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.seugi.designsystem.preview.feature.Avatar
import com.seugi.designsystem.preview.feature.Badge
import com.seugi.designsystem.preview.feature.BottomNavigation
import com.seugi.designsystem.preview.feature.Button
import com.seugi.designsystem.preview.feature.Category
import com.seugi.designsystem.preview.feature.ChatItem
import com.seugi.designsystem.preview.feature.ChatList
import com.seugi.designsystem.preview.feature.CheckBox
import com.seugi.designsystem.preview.feature.Dialog
import com.seugi.designsystem.preview.feature.Divider
import com.seugi.designsystem.preview.feature.Dropdown
import com.seugi.designsystem.preview.feature.Error
import com.seugi.designsystem.preview.feature.HomeScreen
import com.seugi.designsystem.preview.feature.ListItem
import com.seugi.designsystem.preview.feature.MemberList
import com.seugi.designsystem.preview.feature.OAuthButton
import com.seugi.designsystem.preview.feature.RoomImage
import com.seugi.designsystem.preview.feature.Shadow
import com.seugi.designsystem.preview.feature.Toggle
import com.seugi.designsystem.preview.feature.ToolTip
import com.seugi.designsystem.preview.feature.TopBar
import com.seugi.designsystem.theme.SeugiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            SeugiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = SeugiTheme.colors.white,
                ) {
                    NavHost(
                        navController = navHostController,
                        startDestination = NavRoot.HOME,
                    ) {
                        composable(NavRoot.HOME) {
                            HomeScreen(navController = navHostController)
                        }
                        composable(NavRoot.CHATITEM) {
                            ChatItem()
                        }
                        composable(NavRoot.CHATLIST) {
                            ChatList()
                        }
                        composable(NavRoot.AVATAR) {
                            Avatar()
                        }
                        composable(NavRoot.BADGE) {
                            Badge()
                        }
                        composable(NavRoot.BOTTOMNAV) {
                            BottomNavigation()
                        }
                        composable(NavRoot.BUTTON) {
                            Button()
                        }
                        composable(NavRoot.CATEGORY) {
                            Category()
                        }
                        composable(NavRoot.CHECKBOX) {
                            CheckBox()
                        }
                        composable(NavRoot.DIVIDER) {
                            Divider()
                        }
                        composable(NavRoot.LISTIEM) {
                            ListItem()
                        }
                        composable(NavRoot.MEMBERLIST) {
                            MemberList()
                        }
                        composable(NavRoot.OAUTHBUTTON) {
                            OAuthButton()
                        }
                        composable(NavRoot.TOGGLE) {
                            Toggle()
                        }
                        composable(NavRoot.SHADOW) {
                            Shadow()
                        }
                        composable(NavRoot.DROPDOWN) {
                            Dropdown()
                        }
                        composable(NavRoot.TOPBAR) {
                            TopBar()
                        }
                        composable(NavRoot.TOOLTIP) {
                            ToolTip()
                        }
                        composable(NavRoot.DIALOG) {
                            Dialog()
                        }
                        composable(NavRoot.ERROR) {
                            Error()
                        }
                        composable(NavRoot.ROOMIMAGE) {
                            RoomImage()
                        }
                    }
                }
            }
        }
    }
}
