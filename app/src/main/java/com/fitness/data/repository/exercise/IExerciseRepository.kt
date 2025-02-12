package com.fitness.data.repository.exercise

import com.fitness.model.Exercise
import kotlinx.coroutines.flow.Flow

interface IExerciseRepository {
    fun getAllExercises(): Flow<List<Exercise>>
    suspend fun upsert(exercise: Exercise)
    suspend fun delete(exercise: Exercise)

}