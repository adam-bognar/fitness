package com.fitness.data.streak

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StreakViewModel @Inject constructor(
    private val repository: IStreakRepository
) : ViewModel() {

    init {
        recordActivityCompletion()
    }

    val dailyStreak: StateFlow<StreakData> = repository.getDailyStreak()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = StreakData()
        )

    val weeklyStreak: StateFlow<StreakData> = repository.getWeeklyStreak()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = StreakData()
        )

    fun recordActivityCompletion() {
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            try {
                repository.updateDailyStreak(now)
                repository.updateWeeklyStreak(now)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}