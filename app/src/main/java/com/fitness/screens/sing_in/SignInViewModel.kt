package com.fitness.screens.sing_in

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.fitness.MainActivity
import com.fitness.data.auth.AccountService
import com.fitness.model.AppViewModel
import com.fitness.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountService: AccountService,
) : AppViewModel() {
   val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    fun updateEmail(email: String) {
        this.email.value = email
    }

    fun updatePassword(password: String) {
        this.password.value = password
    }

    fun onSignInClick(openAndPopUp: (String) -> Unit) {
        launchCatching {
            accountService.signIn(email.value, password.value)
            openAndPopUp(Screen.TEST.route)
        }
    }

    fun onSignUpClick(openAndPopUp: (String) -> Unit) {
        openAndPopUp(Screen.SIGN_UP.route)
    }





}