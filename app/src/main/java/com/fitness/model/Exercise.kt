package com.fitness.model


data class Exercise(
    var id: Int,
    val name: String,
    val lastLog: ExerciseLog? = null
){
    constructor() : this(0, "")
}