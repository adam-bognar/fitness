package com.fitness.data.repository.user_information

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.model.macros.UserInformation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val userInfoRepository: IUserInformationRepository
): ViewModel() {
    private val _userInformation = MutableStateFlow<UserInformation>(UserInformation())
    val userInformation = _userInformation.asStateFlow()

    init {
        getUserInformation()
    }

    private fun getUserInformation() {
        viewModelScope.launch {
            userInfoRepository.getUserInformation().collect {
                _userInformation.value = it
            }
        }
    }

    fun upsert(userInformation: UserInformation) {
        viewModelScope.launch {
            userInfoRepository.upsert(userInformation)
        }
    }

    fun delete(userInformation: UserInformation) {
        viewModelScope.launch {
            userInfoRepository.delete(userInformation)
        }
    }


}