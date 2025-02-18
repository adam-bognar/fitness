package com.fitness.model.macros

data class Food(
    val name: String,
    val calories: Int,
    val protein: Int,
    val fat: Int,
    val carbs: Int,
){
    constructor() : this("", 0, 0, 0, 0)
}
