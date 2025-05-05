
package com.fitness.model.gym

data class ExerciseLog(
    val id: Int,
    val sets: List<RepsToWeight>
){
    constructor(): this(0, listOf())
}
