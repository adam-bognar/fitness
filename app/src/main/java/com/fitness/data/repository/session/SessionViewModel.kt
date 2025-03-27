package com.fitness.data.repository.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.model.gym.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val repository: ISessionRepository
) : ViewModel() {
    private val _list = MutableStateFlow<List<Session>>(listOf())
    val list = _list.asStateFlow()


    init {
        getAllSessions()
    }

    private fun getAllSessions() {
        viewModelScope.launch {
            repository.getAllSessions().collectLatest {
                _list.tryEmit(it)
            }
        }
    }

    fun sessionCount(): Int {
        return _list.value.size
    }

    fun getSession(index: Int): Session {
        return _list.value[index]
    }

    fun upsert(item: Session) {
        viewModelScope.launch {
            try {
                repository.upsert(item)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
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





}