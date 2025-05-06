package com.fitness.screens.profile

import com.fitness.data.auth.AccountService
import com.fitness.model.AppViewModel
import com.fitness.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignOutViewModel @Inject constructor(
    private val accountService: AccountService
) : AppViewModel() {

    fun signOut(onNavigate: (String) -> Unit) {
        launchCatching {
            accountService.signOut()
            onNavigate(Screen.SIGN_IN.route)
        }
    }
}