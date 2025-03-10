package com.fitness.screens.splash

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
    //openAndPopUp(Screen.MAP.route)
        openAndPopUp(Screen.HOME.route)
    }
    else openAndPopUp(Screen.SIGN_IN.route)
  }

}