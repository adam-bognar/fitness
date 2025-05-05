package com.fitness.screens.Running

import android.content.Intent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fitness.R
import com.fitness.data.running.RunningService
import com.fitness.screens.home.TopAppBar
import kotlinx.coroutines.delay

@Composable
fun RunningScreen(
    onNavigateBack: () -> Unit,
) {
    val context = LocalContext.current
    var isRunning by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableIntStateOf(0) }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(1000L)
            elapsedTime++
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                alignment = Alignment.CenterHorizontally,
                onBack = { onNavigateBack },
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(colorResource(id = R.color.background)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpinningGradientCircle(elapsedTime)
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    isRunning = true
                    Intent(context, RunningService::class.java).also {
                        it.action = RunningService.ACTION_START
                        context.startService(it)
                    }
                }) {
                    Text(text = "Start")
                }
                Button(onClick = {
                    isRunning = false
                    Intent(context, RunningService::class.java).also {
                        it.action = RunningService.ACTION_STOP
                        context.startService(it)
                    }
                }) {
                    Text(text = "End")
                }
            }
        }
    )
}

@Composable
fun SpinningGradientCircle(elapsedTime: Int) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val purple = colorResource(R.color.purple)
    val pink = Color.Magenta

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(200.dp)
            .background(
                color = colorResource(R.color.background),
                shape = CircleShape
            )
    ) {
        Canvas(
            modifier = Modifier
                .size(200.dp)
                .rotate(rotation)
        ) {
            drawCircle(
                brush = Brush.sweepGradient(
                    colors = listOf(purple, pink, purple)
                ),
                style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        Text(
            text = formatElapsedTime(elapsedTime),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )
    }
}

fun formatElapsedTime(seconds: Int): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, secs)
}

@Preview
@Composable
fun RunningScreenPreview() {
    RunningScreen(
        onNavigateBack = {}
    )
}
