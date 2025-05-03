package com.fitness.data.streak

import kotlinx.coroutines.flow.Flow

interface IStreakRepository {
    fun getDailyStreak(): Flow<StreakData>
    fun getWeeklyStreak(): Flow<StreakData>
    suspend fun updateDailyStreak(currentDate: Long)
    suspend fun updateWeeklyStreak(currentDate: Long)

     suspend fun resetDailyStreak()
    suspend fun resetWeeklyStreak()
}