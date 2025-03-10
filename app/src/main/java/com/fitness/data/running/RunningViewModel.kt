package com.fitness.data.running

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class RunningViewModel @Inject constructor(
    private val repository: IRunningRepository
):ViewModel()  {
    private val _runningSessions = MutableStateFlow<List<RunningSession>>(emptyList())
    val runningSessions = _runningSessions.asStateFlow()

    init {
        getRunningSessions()
    }

    private fun getRunningSessions() {
        viewModelScope.launch {
            repository.getRunningSessions().collectLatest {
                _runningSessions.value = it
            }
        }
    }

    fun saveRunningSession(session: RunningSession) {
        viewModelScope.launch {
            repository.saveRunningSession(session)
        }
    }


}