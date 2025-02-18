package com.fitness.model.macros

data class UserInformation(
    val age: Int,
    val weight: Double,
    val height: Double,
    val gender: Gender,
    val activityLevel: ActivityLevel,
    val goal: Goal
) {
    constructor() : this(0, 0.0, 0.0, Gender.MALE, ActivityLevel.SEDENTARY, Goal.LOSE_WEIGHT)
}
