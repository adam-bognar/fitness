package com.fitness.navigation

sealed class Screen(val route: String) {
    object SIGN_IN : Screen("SIGN_IN")
    object SIGN_UP : Screen("SIGN_UP")
    object SPLASH : Screen("SPLASH")
    object HOME : Screen("HOME")
    object WORKOUT : Screen("WORKOUT")
    object EDIT_ROUTINE : Screen("EDIT_ROUTINE")
    object WORKOUT_SESSION : Screen("WORKOUT_SESSION")
    object UPLOAD : Screen("UPLOAD")
    object MACROS : Screen("MACROS")
    object MAP : Screen("MAP")
    object RUNNING : Screen("RUNNING")
    object PROFILE : Screen("PROFILE")
}