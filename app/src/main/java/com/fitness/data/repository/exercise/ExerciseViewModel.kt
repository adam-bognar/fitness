package com.fitness.data.repository.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.model.Exercise
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val repository: IExerciseRepository
) : ViewModel() {
    private val _list = MutableStateFlow<List<Exercise>>(listOf())
    val list = _list.asStateFlow()

    init {
        getAllExercises()
    }

    private fun getAllExercises() {
        viewModelScope.launch {
            repository.getAllExercises().collectLatest {
                _list.tryEmit(it)
            }
        }
    }

    fun exerciseCount(): Int {
        return _list.value.size
    }

    fun getExercise(index: Int): Exercise {
        for (exercise in _list.value) {
            if (exercise.id == index) {
                return exercise
            }
        }
        return Exercise()
    }

    fun exerciseExists(id: Int): Boolean {
        for (exercise in _list.value) {
            if (exercise.id == id) {
                return true
            }
        }
        return false
    }

    fun upsert(item: Exercise) {
        viewModelScope.launch {
            try {
                repository.upsert(item)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun delete(item: Exercise) {
        viewModelScope.launch {
            try {
                repository.delete(item)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}