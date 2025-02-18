package com.fitness.data.repository.exercise

import com.fitness.model.gym.GlobalExercise
import kotlinx.coroutines.flow.Flow

interface IGlobalExerciseRepository {
    fun getAllExercises(): Flow<List<GlobalExercise>>
    suspend fun upsert(exercise: GlobalExercise)
}
