package com.fitness.data.repository.macros

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.model.macros.DailyMacroRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MacrosViewModel @Inject constructor(
    private val macrosRepository: IMacrosRepository
) : ViewModel() {

    private val _macros = MutableStateFlow<List<DailyMacroRecord>>(emptyList())
    val macros = _macros.asStateFlow()

    init {
        getMacros()
    }


    private fun getMacros() {
        viewModelScope.launch {
            macrosRepository.getMacros().collect {
                _macros.value = it
            }
        }
    }

    fun highestId(): Int {
        return _macros.value.maxByOrNull { it.id }?.id ?: 0
    }


    fun upsert(macro: DailyMacroRecord) {
        viewModelScope.launch {
            macrosRepository.upsert(macro)
        }
    }

    fun delete(macro: DailyMacroRecord) {
        viewModelScope.launch {
            macrosRepository.delete(macro)
        }
    }
}