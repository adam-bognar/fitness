
package com.fitness.model.gym

data class ExerciseLog(
    val id: Int,
    val sets: List<RepsToWeight> //A dictionary of reps to weight
){
    constructor(): this(0, listOf())
}
