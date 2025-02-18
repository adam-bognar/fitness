package com.fitness.data.repository.exercise

import com.fitness.data.auth.AccountService
import com.fitness.model.gym.Exercise
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserExerciseRepositoryImpl @Inject constructor(
    db: FirebaseFirestore,
    accountService: AccountService
): IUserExerciseRepository {

    private val user = accountService.currentUserId


    private val collection = db.collection("users").document(user).collection("exercises")

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

    override suspend fun upsert(exercise: Exercise) {
        collection.document(exercise.id.toString()).set(exercise).addOnSuccessListener {
            _exercisesFlow.value = _exercisesFlow.value.map { if (it.id == exercise.id) exercise else it }
        }.await()
    }

    override suspend fun delete(exercise: Exercise) {
        collection.document(exercise.id.toString()).delete().await()

    }


}