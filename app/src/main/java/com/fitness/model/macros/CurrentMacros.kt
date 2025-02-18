package com.fitness.model.macros

data class CurrentMacros(
    var currentCalories: Int,
    var currentProtein: Int,
    var currentFat: Int,
    var currentCarbs: Int
){
    constructor() : this(0, 0, 0, 0)

}