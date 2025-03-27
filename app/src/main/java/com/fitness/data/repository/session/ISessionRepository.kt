package com.fitness.data.repository.session

import com.fitness.model.gym.Session
import kotlinx.coroutines.flow.Flow

interface ISessionRepository {

    fun getAllSessions(): Flow<List<Session>>
    suspend fun upsert(session: Session)
    suspend fun highestId(): Int
}