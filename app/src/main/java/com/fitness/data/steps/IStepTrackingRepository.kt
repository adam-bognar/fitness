package com.fitness.data.steps

import kotlinx.coroutines.flow.StateFlow

interface IStepTrackingRepository {
    val stepCount: StateFlow<Int>

    fun startTracking()
    fun stopTracking()
    fun resetSteps()

}