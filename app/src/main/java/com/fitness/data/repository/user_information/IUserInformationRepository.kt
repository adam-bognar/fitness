package com.fitness.data.repository.user_information

import com.fitness.model.macros.UserInformation
import kotlinx.coroutines.flow.Flow

interface IUserInformationRepository {
    fun getUserInformation(): Flow<UserInformation>
    suspend fun upsert(userInformation: UserInformation)
    suspend fun delete(userInformation: UserInformation)
}