package com.apeun.gidaechi.seugi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.apeun.gidaechi.designsystem.theme.SeugiTheme
import com.apeun.gidaechi.main.navigation.MAIN_ROUTE
import com.apeun.gidaechi.main.navigation.mainScreen
import com.apeun.gidaechi.main.navigation.navigateToMain
import com.apeun.gidaechi.navigation.onboardingScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController: NavHostController = rememberNavController()
            SeugiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    NavHost(
                        navController = navHostController,
                        startDestination = MAIN_ROUTE,
                    ) {
                        mainScreen()
                        onboardingScreen(
                            onboardingToMain = {
                                navHostController.navigateToMain()
                            },
                        )
                    }
                }
            }
        }
    }
}
