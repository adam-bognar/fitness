package com.fitness.data.streak

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class StreakInfo(
    val name: String,
    val icon: ImageVector,
    val color: Color,
    val currentStreak: Int,
    val milestone: List<Int>
)