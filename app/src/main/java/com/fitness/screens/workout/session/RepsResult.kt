package com.fitness.screens.workout.session

import java.io.Serializable

data class RepsResult(
    val exerciseName: String,
    val setNumber: Int,
    val reps: Int
): Serializable
