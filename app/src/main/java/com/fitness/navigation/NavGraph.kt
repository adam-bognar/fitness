package com.fitness.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fitness.screens.Test
import com.fitness.screens.sing_in.SignIn
import com.fitness.screens.sing_up.SignUp
import com.fitness.screens.splash.Splash

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SPLASH.route
    ) {
        composable(Screen.SPLASH.route) {
            Splash(
                openAndPopUp = { destination ->
                    navController.navigate(destination)
                }
            )
        }

        composable(Screen.SIGN_IN.route) {
            SignIn(
                open = { destination ->
                    navController.navigate(destination)
                }
            )
        }
        composable(Screen.SIGN_UP.route) {
            SignUp(
                open = { destination ->
                    navController.navigate(destination)
                }
            )

        }
        composable(Screen.TEST.route) {
            Test()
        }
    }
}