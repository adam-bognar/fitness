
package com.fitness.model.gym

data class RepsToWeight(
    val reps: Int,
    val weight: Int
){
    constructor(): this(0, 0)
}
