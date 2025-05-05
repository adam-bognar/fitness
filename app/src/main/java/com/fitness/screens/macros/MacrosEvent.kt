package com.fitness.screens.macros

import com.fitness.model.macros.Food
import com.fitness.model.macros.UserInformation

sealed interface MacrosEvent {
    object ToggleSettings : MacrosEvent
    object ToggleAddFood  : MacrosEvent
    data class SaveSettings(val info: UserInformation) : MacrosEvent
    data class AddFood(val food: Food) : MacrosEvent
    data class DeleteFood(val index: Int) : MacrosEvent
}