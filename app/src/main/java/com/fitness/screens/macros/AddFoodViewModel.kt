package com.fitness.screens.macros

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddFoodViewModel : ViewModel() {
    data class Ui(val name: String = "", val calories: String = "", val protein: String = "", val fat: String = "", val carbs: String = "") {
        val isValid get() = listOf(name, calories, protein, fat, carbs).all { it.isNotBlank() }
    }
    private val _ui = MutableStateFlow(Ui())
    val ui = _ui.asStateFlow()

    fun update(transform: Ui.() -> Ui) { _ui.value = _ui.value.transform() }
}