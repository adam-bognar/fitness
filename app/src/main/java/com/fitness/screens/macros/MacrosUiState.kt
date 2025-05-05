package com.fitness.screens.macros

import com.fitness.model.macros.DailyMacroRecord
import com.fitness.model.macros.UserInformation

data class MacrosUiState(
    val user: UserInformation? = null,
    val today: DailyMacroRecord? = null,
    val showSettings: Boolean = false,
    val showAddFood: Boolean = false,
)