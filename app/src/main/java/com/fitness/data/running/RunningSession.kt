package com.fitness.data.running

data class RunningSession(
    val id: Int,
    val timestamp: Long,
    val steps: Int,
    val coords: List<MyLatLng>,
){
    constructor(): this(0, 0, 0, emptyList())
}
