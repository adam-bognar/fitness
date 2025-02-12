package com.fitness.screens.sign_in

import com.fitness.data.auth.AccountService
import com.fitness.model.AppViewModel
import com.fitness.model.SignInUiState
import com.fitness.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject



@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountService: AccountService,
) : AppViewModel() {


   private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun onSignInClick(openAndPopUp: (String) -> Unit) {
        launchCatching {
            accountService.signIn(uiState.value.email, uiState.value.password)
            openAndPopUp(Screen.HOME.route)
        }
    }

    fun onSignUpClick(openAndPopUp: (String) -> Unit) {
        openAndPopUp(Screen.SIGN_UP.route)
    }





}