package com.fitness.data.streak

data class StreakData(
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val lastActivityDate: Long = 0L
)