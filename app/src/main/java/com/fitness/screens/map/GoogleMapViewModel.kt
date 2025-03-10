package com.fitness.screens.map

import android.location.Location
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GoogleMapViewModel @Inject constructor(
) : ViewModel() {

    private val _state = MutableStateFlow(GoogleMapState())
    val state = _state.asStateFlow()


    private val _polylinePoints = MutableStateFlow<List<LatLng>>(
        listOf(
            LatLng(37.7749, -122.4194), // San Francisco
            LatLng(34.0522, -118.2437), // Los Angeles
            LatLng(36.7783, -119.4179)  // California Center
        )
    )

    val polylinePoints = _polylinePoints.asStateFlow()


    fun calculateTotalDistance(points: List<LatLng>): Float {
        var totalDistance = 0f
        for (i in 0 until points.size - 1) {
            val result = FloatArray(1)
            Location.distanceBetween(
                points[i].latitude, points[i].longitude,
                points[i + 1].latitude, points[i + 1].longitude,
                result
            )
            totalDistance += result[0] // Accumulate distance
        }
        return totalDistance
    }



    fun collectLatLngs(): List<LatLng> {
        val list: MutableList<LatLng> = mutableListOf()
        return list
    }
}

data class GoogleMapState(
    val cameraPosition: CameraPosition = CameraPosition.fromLatLngZoom(LatLng(47.0, 19.0), 10f),
    val searchTerm: String = "",
    val isHeatMapOn: Boolean = false,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val isError: Boolean = error != null,
)
