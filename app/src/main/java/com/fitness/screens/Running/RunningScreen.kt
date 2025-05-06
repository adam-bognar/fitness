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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.unit.sp
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
                onBack = onNavigateBack,
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(colorResource(id = R.color.background))
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpinningGradientCircle(elapsedTime)
                Spacer(modifier = Modifier.height(48.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            if (!isRunning) {
                                isRunning = true
                                Intent(context, RunningService::class.java).also {
                                    it.action = RunningService.ACTION_START
                                    context.startService(it)
                                }
                            }
                        },
                        enabled = !isRunning,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.purple),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text(text = "Start", fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = {
                            if (isRunning) {
                                isRunning = false
                                Intent(context, RunningService::class.java).also {
                                    it.action = RunningService.ACTION_STOP
                                    context.startService(it)
                                }
                            }
                        },
                        enabled = isRunning,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text(text = "End", fontSize = 16.sp)
                    }
                }
            }
        }
    )
}

@Composable
fun SpinningGradientCircle(elapsedTime: Int) {
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "rotationValue"
    )

    val purple = colorResource(R.color.purple)
    val pink = colorResource(R.color.pink)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(250.dp)
            .clip(CircleShape)
            .background(
                color = colorResource(R.color.background),
            )
    ) {
        Canvas(
            modifier = Modifier
                .size(250.dp)
                .rotate(rotation)
        ) {
            drawCircle(
                brush = Brush.sweepGradient(
                    colors = listOf(purple, pink, purple)
                ),
                style = Stroke(width = 15.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = formatElapsedTime(elapsedTime),
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 40.sp),
            color = colorResource(id = R.color.text_color),
            fontWeight = FontWeight.Bold
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