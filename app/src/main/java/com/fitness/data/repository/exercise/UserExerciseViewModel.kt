package com.fitness.data.repository.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.model.gym.Exercise
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserExerciseViewModel @Inject constructor(
    private val userExerciseRepository: IUserExerciseRepository
) : ViewModel() {

    private val _userExercises = MutableStateFlow<List<Exercise>>(listOf())
    val userExercises = _userExercises.asStateFlow()

    init {
        getUserExercises()
    }

    private fun getUserExercises() {
        viewModelScope.launch {
            userExerciseRepository.getAllExercises().collect {
                _userExercises.value = it
            }
        }
    }

    fun upsertUserExercise(exercise: Exercise) {
        viewModelScope.launch {
            userExerciseRepository.upsert(exercise)
        }
    }

    fun deleteUserExercise(exercise: Exercise) {
        viewModelScope.launch {
            userExerciseRepository.delete(exercise)
        }
    }
}
