package com.fitness.data.repository.macros

import com.fitness.model.macros.DailyMacroRecord
import kotlinx.coroutines.flow.Flow

interface IMacrosRepository {
    fun getMacros(): Flow<List<DailyMacroRecord>>
    suspend fun upsert(macro: DailyMacroRecord)
    suspend fun delete(macro: DailyMacroRecord)
}