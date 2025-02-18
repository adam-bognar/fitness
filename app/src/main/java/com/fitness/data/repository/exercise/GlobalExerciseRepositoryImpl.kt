package com.fitness.data.repository.exercise

import com.fitness.model.gym.GlobalExercise
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class GlobalExerciseRepositoryImpl @Inject constructor(
    db: FirebaseFirestore,
): IGlobalExerciseRepository {



    private val collection = db.collection("exercises")

    private val _exercisesFlow = MutableStateFlow<List<GlobalExercise>>(emptyList())

    init {
        collection.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val exercises = snapshot.documents.mapNotNull { it.toObject(GlobalExercise::class.java) }
                _exercisesFlow.value = exercises
            }
        }
    }

    override fun getAllExercises(): Flow<List<GlobalExercise>> = _exercisesFlow.asStateFlow()

    override suspend fun upsert(exercise: GlobalExercise) {
        collection.document(exercise.id.toString()).set(exercise)
    }


}