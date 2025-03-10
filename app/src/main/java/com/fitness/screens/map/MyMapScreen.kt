package com.fitness.screens.map
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MyMapScreen(
    viewModel: GoogleMapViewModel = hiltViewModel()
) {
    Box(modifier = Modifier.fillMaxSize()){

        val state by viewModel.state.collectAsStateWithLifecycle()
        val polylinePoints by viewModel.polylinePoints.collectAsStateWithLifecycle()
        GoogleMap(
            modifier = Modifier.fillMaxSize(),

            cameraPositionState = rememberCameraPositionState { position = state.cameraPosition },
            properties = MapProperties(
                mapType = MapType.NORMAL,
                isMyLocationEnabled = true
            ),
            uiSettings = MapUiSettings(
                zoomControlsEnabled = true,
                zoomGesturesEnabled = true,
                scrollGesturesEnabled = true,
                compassEnabled = true,
                mapToolbarEnabled = false,
                myLocationButtonEnabled = true,
            )
        ){
            Polyline(
                points = polylinePoints,
                color = Color.Blue,
                width = 10f
            )

        }
    }
}