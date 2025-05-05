package com.fitness.presentation.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import androidx.wear.tooling.preview.devices.WearDevices
import com.fitness.presentation.screen.RunningViewModel.RunningEvent
import kotlinx.coroutines.delay

@Composable
fun RunningScreen(
    viewModel: RunningViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val isRunning = state.value.running
    Log.d("WearOSLog", "RunningScreen: $isRunning")
    var elapsedTime by remember { mutableIntStateOf(0) }
    var text by remember { mutableStateOf("Start") }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (true) {
                delay(1000L)
                elapsedTime++
            }
        } else {
            elapsedTime = 0
        }
    }



    Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = formatElapsedTime(elapsedTime),
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    if (!isRunning) {
                        text = "Stop"
                        viewModel.onEvent(RunningEvent.StartClicked)
                    } else {
                        text = "Start"
                        viewModel.onEvent(RunningEvent.EndClicked)
                    }


                },
                ) {
                    Text(text = text)
                }
            }
        }



fun formatElapsedTime(seconds: Int): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, secs)
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun RunningScreenPreview() {
    RunningScreen()
}