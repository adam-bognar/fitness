package com.fitness.screens.sign_up

import com.fitness.data.auth.AccountService
import com.fitness.model.AppViewModel
import com.fitness.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject



@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
) : AppViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
    }

    private fun passwordsMatch(): Boolean {
        return uiState.value.password == uiState.value.confirmPassword
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
            accountService.signUp(uiState.value.email, uiState.value.password)

            openAndPopUp(Screen.HOME.route)

        }
    }



}