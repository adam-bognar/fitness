package com.fitness.data.running

import kotlinx.coroutines.flow.Flow

interface IRunningRepository {
    suspend fun saveRunningSession(session: RunningSession)
    fun getRunningSessions(): Flow<List<RunningSession>>
    fun highestId(): Int
}
