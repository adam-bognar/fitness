package com.fitness.data.running

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class RunningSession(
    val id: Int,
    val timestamp: Long,
    val steps: Int,
    val coords: List<MyLatLng>,
    val distance: Double,
    @ServerTimestamp val date: Timestamp? = null,

    ){
    constructor(): this(0, 0, 0, emptyList(), 0.0, null)

    fun calculateTotalDistance(points: List<LatLng>): Double {
        var totalDistance = 0.0
        for (i in 0 until points.size - 1) {
            val result = FloatArray(1)
            Location.distanceBetween(
                points[i].latitude, points[i].longitude,
                points[i + 1].latitude, points[i + 1].longitude,
                result
            )
            totalDistance += result[0]
        }
        return totalDistance
    }
}
