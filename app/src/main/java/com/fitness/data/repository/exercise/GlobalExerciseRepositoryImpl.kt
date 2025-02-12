package com.fitness.data.repository.exercise

import com.fitness.model.Exercise
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class GlobalExerciseRepositoryImpl @Inject constructor(
    db: FirebaseFirestore,
): IGlobalExerciseRepository {



    private val collection = db.collection("exercises")

    private val _exercisesFlow = MutableStateFlow<List<Exercise>>(emptyList())

    init {
        collection.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val exercises = snapshot.documents.mapNotNull { it.toObject(Exercise::class.java) }
                _exercisesFlow.value = exercises
            }
        }
    }

    override fun getAllExercises(): Flow<List<Exercise>> = _exercisesFlow.asStateFlow()


}