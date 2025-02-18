package com.fitness.model.gym


data class Routine(
    val id: Int,
    val name: String,
    val description: String,
    var exercises: List<Exercise> = emptyList()
){
    constructor() : this(0, "", "", emptyList())
}