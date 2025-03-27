package com.fitness.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.fitness.presentation.data.ISendDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RunningViewModel @Inject constructor(
    private val repo: ISendDataRepository
) : ViewModel() {
    private val _state = MutableStateFlow(RunningScreenState())
    val state = _state.asStateFlow()

    fun onEvent(event: RunningEvent) {
        when (event) {

            is RunningEvent.StartClicked -> {
                repo.sendMessage("started")
                _state.update { it.copy(true) }
                Log.d("WearOSLog", _state.value.running.toString())

            }

            is RunningEvent.EndClicked -> {
                repo.sendMessage("finished")
                _state.update { it.copy(false) }
                Log.d("WearOSLog", _state.value.running.toString())

            }


        }
    }

    data class RunningScreenState(
        val running: Boolean = false
    )

    sealed class RunningEvent {
        object StartClicked : RunningEvent()
        object EndClicked : RunningEvent()

    }

}