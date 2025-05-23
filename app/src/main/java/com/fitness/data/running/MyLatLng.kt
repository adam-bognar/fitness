package com.fitness.data.running

import com.google.android.gms.maps.model.LatLng

data class MyLatLng(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) {
    constructor(): this(0.0, 0.0)

    fun toLatLng(): LatLng {
        return LatLng(latitude, longitude)
    }

    companion object {
        fun fromLatLng(latLng: LatLng): MyLatLng {
            return MyLatLng(latLng.latitude, latLng.longitude)
        }
    }
}
