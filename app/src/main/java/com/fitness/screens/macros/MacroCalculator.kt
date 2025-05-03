package com.fitness.screens.macros

import com.fitness.model.macros.ActivityLevel
import com.fitness.model.macros.Gender
import com.fitness.model.macros.Goal
import com.fitness.model.macros.TargetMacros
import com.fitness.model.macros.UserInformation
import javax.inject.Inject

class MacroCalculator @Inject constructor() {
    fun calculate(user: UserInformation): TargetMacros {
        val bmr = if (user.gender == Gender.MALE) {
            (9.9 * user.weight) + (6.25 * user.height) - (4.92 * user.age) + 5
        } else {
            (9.9 * user.weight) + (6.25 * user.height) - (4.92 * user.age) - 161
        }
        val multiplier = when (user.activityLevel) {
            ActivityLevel.SEDENTARY -> 1.2
            ActivityLevel.LIGHTLY_ACTIVE -> 1.375
            ActivityLevel.MODERATELY_ACTIVE -> 1.55
            ActivityLevel.ACTIVE -> 1.725
            ActivityLevel.VERY_ACTIVE -> 1.9
        }
        var tdee = bmr * multiplier
        tdee = when (user.goal) {
            Goal.LOSE_WEIGHT -> tdee - 500
            Goal.GAIN_WEIGHT -> tdee + 300
            else -> tdee
        }
        val protein = (2.0 * user.weight).toInt()
        val fat = ((tdee * 0.25) / 9).toInt()
        val carbs = ((tdee - (protein * 4 + fat * 9)) / 4).toInt()
        return TargetMacros(tdee.toInt(), protein, fat, carbs)
    }
}