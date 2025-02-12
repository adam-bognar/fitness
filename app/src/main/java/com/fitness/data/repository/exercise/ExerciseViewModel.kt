package com.fitness.data.repository.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.model.Exercise
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val userExerciseRepository: IUserExerciseRepository,
    private val globalExerciseRepository: IGlobalExerciseRepository
) : ViewModel() {

    private val _userExercises = MutableStateFlow<List<Exercise>>(listOf())
    val userExercises = _userExercises.asStateFlow()

    private val _globalExercises = MutableStateFlow<List<Exercise>>(listOf())
    val globalExercises = _globalExercises.asStateFlow()

    init {
        getUserExercises()
        getGlobalExercises()
    }

    private fun getUserExercises() {
        viewModelScope.launch {
            userExerciseRepository.getAllExercises().collect {
                _userExercises.value = it
            }
        }
    }

    private fun getGlobalExercises() {
        viewModelScope.launch {
            globalExerciseRepository.getAllExercises().collect {
                _globalExercises.value = it
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
