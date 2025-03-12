package com.fitness.screens.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitness.R
import com.fitness.data.running.MyLatLng
import com.fitness.data.running.RunningSession
import com.fitness.screens.home.TopAppBar
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MyMapScreen(
    session: RunningSession,
    onNavigateBack: () -> Unit
) {

    val coords = session.coords.map { LatLng(it.latitude, it.longitude) }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    alignment = Alignment.CenterHorizontally,
                    onBack = onNavigateBack,
                )
                DistanceStepsDurationSection(session.distance, session.steps, session.timestamp.toInt())
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(coords[0], 15f)
                },
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
            ) {
                Polyline(
                    points = coords,
                    color = Color.Blue,
                    width = 10f
                )
            }
        }
    }
}

@Composable
fun DistanceStepsDurationSection(distance: Double, steps: Int ,duration: Int) {

    val time = formatElapsedTime(duration)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.background))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Distance: $distance",
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Steps: $steps",
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Duration: $time",
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

private fun formatElapsedTime(seconds: Int): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, secs)
}

@Preview
@Composable
fun MyMapScreenPreview() {
    MyMapScreen(
        session = RunningSession(
            id = 1,
            steps = 1000,
            distance = 1000.0,
            timestamp = 14056,
            coords = emptyList<MyLatLng>(),
            date = Timestamp.now(),

        ),
        onNavigateBack = {}
    )
}
