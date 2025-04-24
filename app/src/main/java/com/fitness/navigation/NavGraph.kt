package com.fitness.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fitness.UploadJsonScreen
import com.fitness.data.running.RunningSession
import com.fitness.model.gym.Routine
import com.fitness.screens.Running.RunningScreen
import com.fitness.screens.camera.PoseCameraScreen
import com.fitness.screens.home.Home
import com.fitness.screens.macros.Macros
import com.fitness.screens.map.MyMapScreen
import com.fitness.screens.profile.ProfileScreen
import com.fitness.screens.sign_in.SignIn
import com.fitness.screens.sign_up.SignUp
import com.fitness.screens.splash.Splash
import com.fitness.screens.workout.routine.EditRoutine
import com.fitness.screens.workout.routine.Routines
import com.fitness.screens.workout.session.WorkoutPage
import com.google.gson.Gson

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SPLASH.route
    ) {
        composable(Screen.UPLOAD.route) {
            UploadJsonScreen()
        }

        composable(Screen.SPLASH.route) {
            Splash(
                openAndPopUp = { destination ->
                    navController.navigate(destination)
                }
            )
        }

        composable(Screen.RUNNING.route) {
            RunningScreen(onNavigateBack = {
                navController.popBackStack()
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
                },
                onNavigate = { destination ->
                    navController.navigate(destination)
                }
            )
        }

        composable(Screen.WORKOUT.route) {
            Routines(
                onStart = { routine ->
                    val routineJson = Gson().toJson(routine)
                    navController.navigate("${Screen.WORKOUT_SESSION.route}/$routineJson")
                },
                onEdit = { id ->
                    navController.navigate("${Screen.EDIT_ROUTINE.route}/$id")
                },
                onClick = { destination ->
                    navController.navigate(destination)
                }
            )
        }
        composable(
            route = "${Screen.EDIT_ROUTINE.route}/{routineID}",
            arguments = listOf(navArgument("routineID") { type = NavType.IntType })
        ) { backStackEntry ->
            val routineID = backStackEntry.arguments?.getInt("routineID") ?: 0
            EditRoutine(
                routineID = routineID,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "${Screen.MAP.route}/{session}",
            arguments = listOf(navArgument("session") { type = NavType.StringType })
        ) { backStackEntry ->
            val sessionJson = backStackEntry.arguments?.getString("session")
            val session = Gson().fromJson(sessionJson, RunningSession::class.java)
            MyMapScreen(
                session = session,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "${Screen.WORKOUT_SESSION.route}/{routine}",
            arguments = listOf(navArgument("routine") { type = NavType.StringType })
        ) { backStackEntry ->
            val routineJson = backStackEntry.arguments?.getString("routine")
            val routine = Gson().fromJson(routineJson, Routine::class.java)
            WorkoutPage(
                routine = routine,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateCamera = { exerciseAndSet ->
                    val exerciseAndSetJson = Gson().toJson(exerciseAndSet)
                    navController.navigate("${Screen.CAMERA.route}/$exerciseAndSetJson")
                },
                navController = navController,
            )
        }

        composable(
            route = "${Screen.CAMERA.route}/{exerciseAndSet}",
            arguments = listOf(navArgument("exerciseAndSet") { type = NavType.StringType })
        ) { backStackEntry ->
            val exerciseAndSetJson = backStackEntry.arguments?.getString("exerciseAndSet")
            val typeToken = object : com.google.gson.reflect.TypeToken<Pair<String, Int>>() {}.type
            val exerciseAndSet: Pair<String, Int>? = Gson().fromJson(exerciseAndSetJson, typeToken)

            val exercise = exerciseAndSet?.first ?: "Unknown"
            val set = exerciseAndSet?.second ?: 0

            PoseCameraScreen(
                exercise = exercise,
                set = set,
                navController = navController
            )
        }

        composable(Screen.MACROS.route) {
            Macros(
                onNavigateTo = { destination ->
                    navController.navigate(destination)
                }
            )
        }

        composable(Screen.PROFILE.route) {
            ProfileScreen(
                onNavigate = { destination ->
                    navController.navigate(destination)
                }
            )
        }
    }
}