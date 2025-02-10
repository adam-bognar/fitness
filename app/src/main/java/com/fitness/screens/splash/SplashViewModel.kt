package com.fitness.screens.splash

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.fitness.MainActivity
import com.fitness.data.auth.AccountService
import com.fitness.model.AppViewModel
import com.fitness.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  private val accountService: AccountService
) : AppViewModel() {

  fun onAppStart(openAndPopUp: (String) -> Unit) {
    if (accountService.hasUser()){

        openAndPopUp(Screen.TEST.route)
    }
    else openAndPopUp(Screen.SIGN_IN.route)
  }

}