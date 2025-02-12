
package com.fitness.data.repository.routine

import com.fitness.model.Routine
import kotlinx.coroutines.flow.Flow

interface IRoutineRepository {
    fun getAllRoutines(): Flow<List<Routine>>
    suspend fun upsert(routine: Routine)
    suspend fun delete(routine: Routine)
    suspend fun highestId(): Int
}
