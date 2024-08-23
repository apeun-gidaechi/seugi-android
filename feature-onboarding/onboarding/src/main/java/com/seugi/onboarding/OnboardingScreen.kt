package com.seugi.onboarding

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.seugi.join.navigation.emailSignUpScreen
import com.seugi.join.navigation.emailVerificationScreen
import com.seugi.join.navigation.navigateToEmailSignUp
import com.seugi.join.navigation.navigateToEmailVerification
import com.seugi.join.navigation.navigateToOAuthSignUp
import com.seugi.join.navigation.oauthSignUp
import com.seugi.login.navigation.emailSignInScreen
import com.seugi.login.navigation.navigateToEmailSignIn
import com.seugi.start.navigation.START_ROUTE
import com.seugi.start.navigation.navigateToStart
import com.seugi.start.navigation.startScreen

private const val NAVIGATION_ANIM = 400

@Composable
internal fun OnboardingScreen(navHostController: NavHostController = rememberNavController(), onboardingToMain: () -> Unit) {
    NavHost(
        navController = navHostController,
        startDestination = START_ROUTE,
        enterTransition = { fadeIn(animationSpec = tween(NAVIGATION_ANIM)) },
        exitTransition = { fadeOut(animationSpec = tween(NAVIGATION_ANIM)) },
        popEnterTransition = { fadeIn(animationSpec = tween(NAVIGATION_ANIM)) },
        popExitTransition = { fadeOut(animationSpec = tween(NAVIGATION_ANIM)) },
    ) {
        startScreen(
            navigateToEmailSignIn = { navHostController.navigateToEmailSignIn() },
            navigateToOAuthSignIn = { navHostController.navigateToOAuthSignUp() },
        )
        emailSignInScreen(
            onboardingToMain = onboardingToMain,
            navigateToOAuthSignUp = { navHostController.navigateToEmailSignUp() },
            popBackStack = { navHostController.navigateToStart() },
        )
        emailSignUpScreen(
            navigateToEmailVerification = { name, email, password ->
                navHostController.navigateToEmailVerification(
                    name = name,
                    email = email,
                    password = password,
                )
            },
            popBackStack = { navHostController.navigateToStart() },
        )
        emailVerificationScreen(
            navigateToStart = { navHostController.navigateToStart() },
            popBackStack = { navHostController.navigateToStart() },
        )

        oauthSignUp(
            popBackStack = { navHostController.popBackStack() },
            navigateToEmailSignUp = { navHostController.navigateToEmailSignUp() },
        )
    }
}
