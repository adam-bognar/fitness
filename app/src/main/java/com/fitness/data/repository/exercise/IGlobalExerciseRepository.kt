package com.fitness.data.repository.exercise

import com.fitness.model.Exercise
import kotlinx.coroutines.flow.Flow

interface IGlobalExerciseRepository {
    fun getAllExercises(): Flow<List<Exercise>>
}
