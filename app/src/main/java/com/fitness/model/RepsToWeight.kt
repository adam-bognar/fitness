
package com.fitness.model

data class RepsToWeight(
    val reps: Int,
    val weight: Int
){
    constructor(): this(0, 0)
}
