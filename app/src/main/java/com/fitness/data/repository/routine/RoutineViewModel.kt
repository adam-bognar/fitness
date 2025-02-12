
package com.fitness.data.repository.routine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.model.Routine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val repository: IRoutineRepository
) : ViewModel() {
    private val _list = MutableStateFlow<List<Routine>>(listOf())
    val list = _list.asStateFlow()


    init {
        getAllRoutines()
    }

    private fun getAllRoutines() {


        viewModelScope.launch {
            repository.getAllRoutines().collectLatest {
                _list.tryEmit(it)
            }
        }
    }

    fun routineCount(): Int {
        return _list.value.size
    }

    fun getRoutine(id: Int): Routine {
        for (routine in _list.value) {
            if (routine.id == id) {
                return routine
            }
        }
        return Routine()
    }

    fun highestId(): Int {
        var highestId = 0
        viewModelScope.launch {
            try {
                highestId = repository.highestId()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return highestId
    }

    fun upsert(item: Routine) {
        viewModelScope.launch {
            try {
                repository.upsert(item)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun delete(item: Routine) {
        viewModelScope.launch {
            try {
                repository.delete(item)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun save() {
        viewModelScope.launch {
            try {
                repository.save()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}
