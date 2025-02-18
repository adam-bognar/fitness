package com.fitness.model.macros

data class TargetMacros(
    val targetCalories: Int,
    val targetProtein: Int,
    val targetFat: Int,
    val targetCarbs: Int
){
    constructor() : this(0, 0, 0, 0)

}