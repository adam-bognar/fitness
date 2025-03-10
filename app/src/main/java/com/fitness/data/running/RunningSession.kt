package com.fitness.data.running

import com.google.android.gms.maps.model.LatLng

data class RunningSession(
    val id: Int,
    val timestamp: Long,
    val steps: Int,
    val coords: List<LatLng>,
){
    constructor(): this(0, 0, 0, emptyList())
}
