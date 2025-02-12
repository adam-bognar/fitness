package com.fitness.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fitness.model.Routine
import com.fitness.screens.home.Home
import com.fitness.screens.sign_in.SignIn
import com.fitness.screens.sign_up.SignUp
import com.fitness.screens.splash.Splash
import com.fitness.screens.workout.routine.EditRoutine
import com.fitness.screens.workout.routine.Routines

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
        composable(Screen.HOME.route) {
            Home(
                onActivityClick = { destination ->
                    navController.navigate(destination)
                }
            )
        }
        composable(Screen.WORKOUT.route) {
            // Workout()
            Routines(
                onStart = {
                    //navController.navigate()
                },
                onEdit = {
                    navController.navigate(Screen.EDIT_ROUTINE.route)
                }
            )
        }
        composable(Screen.EDIT_ROUTINE.route) {
            EditRoutine(
                routine = Routine(),
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}