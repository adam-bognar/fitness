package com.fitness.screens.sing_up

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
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
) : AppViewModel() {
   val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")

    fun updateEmail(email: String) {
        this.email.value = email
    }

    fun updatePassword(password: String) {
        this.password.value = password
    }

    fun updateConfirmPassword(confirmPassword: String) {
        this.confirmPassword.value = confirmPassword
    }

    private fun passwordsMatch(): Boolean {
        return password.value == confirmPassword.value
    }

    fun onSignInClick(openAndPopUp: (String) -> Unit) {
        launchCatching {

            openAndPopUp(Screen.SIGN_IN.route)
        }
    }

    fun onSignUpClick(openAndPopUp: (String) -> Unit) {
        if (!passwordsMatch()) {
            //TODO
            return
        }
        launchCatching {
            accountService.signUp(email.value, password.value)

            openAndPopUp(Screen.TEST.route)

        }
    }



}