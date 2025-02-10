package com.fitness.navigation

sealed class Screen(val route: String) {
    object SIGN_IN : Screen("SIGN_IN")
    object SIGN_UP : Screen("SIGN_UP")
    object TEST : Screen("TEST")
    object SPLASH : Screen("SPLASH")
}