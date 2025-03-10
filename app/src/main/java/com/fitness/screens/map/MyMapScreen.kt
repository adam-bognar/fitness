package com.fitness.screens.map
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fitness.data.running.RunningViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MyMapScreen(
    viewModel: RunningViewModel = hiltViewModel()
) {
    Box(modifier = Modifier.fillMaxSize()){

        val ize = viewModel.runningSessions.collectAsStateWithLifecycle()
        val izebize = ize.value[0]

        GoogleMap(
            modifier = Modifier.fillMaxSize(),

            cameraPositionState = rememberCameraPositionState { position = CameraPosition.fromLatLngZoom(izebize.coords[0], 10f) },
            properties = MapProperties(
                mapType = MapType.NORMAL,
                isMyLocationEnabled = true
            ),
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                zoomGesturesEnabled = true,
                scrollGesturesEnabled = true,
                compassEnabled = false,
                mapToolbarEnabled = false,
                myLocationButtonEnabled = false,
            )
        ){
            Polyline(
                points = izebize.coords,
                color = Color.Blue,
                width = 10f
            )

        }
    }
}