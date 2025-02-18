package com.fitness.model.gym

data class GlobalExercise(
    var id: Int,
    val name: String,
    val muscle: String,
){
    constructor() : this(0, "", "")
}