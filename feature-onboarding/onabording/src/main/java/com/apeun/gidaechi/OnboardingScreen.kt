package com.apeun.gidaechi

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.apeun.gidaechi.login.navigation.emailSignInScreen
import com.apeun.gidaechi.navigation.START_ROUTE
import com.apeun.gidaechi.navigation.emailSignUpScreen
import com.apeun.gidaechi.navigation.emailVerificationScreen
import com.apeun.gidaechi.navigation.joinSuccess
import com.apeun.gidaechi.navigation.oauthSignUp
import com.apeun.gidaechi.navigation.schoolCode
import com.apeun.gidaechi.navigation.selectingJob
import com.apeun.gidaechi.navigation.startScreen
import com.apeun.gidaechi.navigation.waitingJoin

private const val NAVIGATION_ANIM = 400

@Composable
internal fun OnboardingScreen(navHostController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navHostController,
        startDestination = START_ROUTE,
        enterTransition = { fadeIn(animationSpec = tween(NAVIGATION_ANIM)) },
        exitTransition = { fadeOut(animationSpec = tween(NAVIGATION_ANIM)) },
        popEnterTransition = { fadeIn(animationSpec = tween(NAVIGATION_ANIM)) },
        popExitTransition = { fadeOut(animationSpec = tween(NAVIGATION_ANIM)) },
    ) {
        startScreen(navHostController)
        emailSignInScreen(navHostController)
        emailSignUpScreen(navHostController)
        emailVerificationScreen(navHostController)
        schoolCode(navHostController)
        joinSuccess(navHostController)
        selectingJob(navHostController)
        waitingJoin(navHostController)
        oauthSignUp(navHostController)
    }
}
