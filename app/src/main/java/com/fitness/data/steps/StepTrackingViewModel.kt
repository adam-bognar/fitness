package com.fitness.data.steps

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class StepTrackingViewModel @Inject constructor(
    private val repository: StepTrackingRepository
) : ViewModel() {

    val stepCount: StateFlow<Int> = repository.stepCount

    fun startTracking() {
        repository.startTracking()
    }

    fun stopTracking() {
        repository.stopTracking()
    }

    fun resetSteps() {
        repository.resetSteps()
    }

}
