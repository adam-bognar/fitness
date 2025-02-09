package com.fitness.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fitness.screens.Test
import com.fitness.screens.sing_in.SignIn
import com.fitness.screens.sing_up.SignUp

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SIGN_IN.route
    ) {
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