package com.fitness.data.repository.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.model.gym.GlobalExercise
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GlobalExerciseViewModel @Inject constructor(
    private val globalExerciseRepository: IGlobalExerciseRepository
) : ViewModel() {

    private val _globalExercises = MutableStateFlow<List<GlobalExercise>>(listOf())
    val globalExercises = _globalExercises.asStateFlow()

    init {
        getGlobalExercises()
    }

    private fun getGlobalExercises() {
        viewModelScope.launch {
            globalExerciseRepository.getAllExercises().collect {
                _globalExercises.value = it
            }
        }
    }

    fun upsert(globalExercise: GlobalExercise) {
        viewModelScope.launch {
            globalExerciseRepository.upsert(globalExercise)
        }
    }
}
